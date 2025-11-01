package com.example.tpo.model.dominio;

import java.util.List;

import com.example.tpo.model.tipo.ITipoRol;

public class Rol {
    private String nombre;
    private ITipoRol tipoRol;
    private List<Juego> juegosDisponibles;

    public void agregarJuego(Juego j) {}
    public void eliminarJuego() {}

    // Simple role constants to support Main's switch without changing behavior
    public static final Rol TOP = new Rol();
    public static final Rol JUNGLE = new Rol();
    public static final Rol MID = new Rol();
    public static final Rol ADC = new Rol();
    public static final Rol SUPPORT = new Rol();
}
