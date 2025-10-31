package com.example.tpo.model.dominio;

import java.util.List;

import com.example.tpo.model.estado.mail.IEstadoMail;

public class Usuario {
    private String nombre;
    private String apellido;
    private String nombreUsuario;
    private int idUsuario;
    private String email;
    private Juego juegoPrincipal;
    private Rango rango;
    private Rol rol;
    private Region region;
    private List<Disponibilidad> disponibilidadHoraria;
    private IEstadoMail estadoMail;
    private List<Penalidad> penalidades;
    private String passwordHash;
    private List<Rol> rolesPreferidos;
    private String preferencias;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Rango getRango() { return rango; }
    public void setRango(Rango rango) { this.rango = rango; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
    public Region getRegion() { return region; }
    public void setRegion(Region region) { this.region = region; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public IEstadoMail getEstadoMail() { return estadoMail; }
    public void setEstadoMail(IEstadoMail estadoMail) { this.estadoMail = estadoMail; }

    public void verificacionEmail() { if (estadoMail != null) estadoMail.verificarMail(this); }
    public void editarPerfil() {}
    public void iniciarSesion(String nombreUsuario, String contrasenia) {}
}
