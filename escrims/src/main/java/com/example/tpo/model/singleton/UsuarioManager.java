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

    public Usuario registrar(String nombre, String apellido, String nombreUsuario, String email, String contrasenia) { return null; }
    public Usuario autenticar(String email, String contrasenia) { return null; }
    public void eliminarUsuario(String email) {}
}

