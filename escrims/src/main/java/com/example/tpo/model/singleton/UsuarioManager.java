package com.example.tpo.model.singleton;

import java.util.ArrayList;
import java.util.List;

import com.example.tpo.model.dominio.Usuario;

public class UsuarioManager {
    private final List<Usuario> usuarios = new ArrayList<>();
    private static UsuarioManager instancia;

    private UsuarioManager() {}

    public static synchronized UsuarioManager getInstancia() {
        if (instancia == null) instancia = new UsuarioManager();
        return instancia;
    }

    public Usuario registrar(String nombre, String apellido, String nombreUsuario, String email, String contrasenia) {
        if (email == null || email.isBlank()) return null;
        if (usuarios.stream().anyMatch(u -> email.equalsIgnoreCase(u.getEmail()))) return null;
        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setApellido(apellido);
        u.setNombreUsuario(nombreUsuario);
        u.setEmail(email);
        u.setPasswordHash(hash(contrasenia));
        u.setEstadoMail(new com.example.tpo.model.estado.mail.MailPendiente());
        u.verificacionEmail();
        usuarios.add(u);
        return u;
    }

    public Usuario autenticar(String email, String contrasenia) {
        if (email == null || contrasenia == null) return null;
        String h = hash(contrasenia);
        return usuarios.stream()
                .filter(u -> email.equalsIgnoreCase(u.getEmail()) && h.equals(u.getPasswordHash()))
                .findFirst().orElse(null);
    }

    public void eliminarUsuario(String email) {
        usuarios.removeIf(u -> email != null && email.equalsIgnoreCase(u.getEmail()));
    }

    public Usuario getByEmail(String email) {
        if (email == null) return null;
        return usuarios.stream().filter(u -> email.equalsIgnoreCase(u.getEmail())).findFirst().orElse(null);
    }

    public List<Usuario> listar() { return new ArrayList<>(usuarios); }

    private String hash(String input) {
        return input == null ? "" : ("sim-" + input);
    }
}
