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

    public void verificacionEmail() {}
    public void editarPerfil() {}
    public void iniciarSesion(String nombreUsuario, String contrasenia) {}
}

