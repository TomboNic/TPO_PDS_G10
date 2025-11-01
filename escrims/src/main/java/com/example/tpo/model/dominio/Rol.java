package com.example.tpo.model.dominio;

import java.util.List;

import com.example.tpo.model.tipo.ITipoRol;

public class Rol {
    public static final Rol TOP = new Rol("TOP");
    public static final Rol JUNGLE = new Rol("JUNGLE");
    public static final Rol MID = new Rol("MID");
    public static final Rol ADC = new Rol("ADC");
    public static final Rol SUPPORT = new Rol("SUPPORT");

    private String nombre;
    private ITipoRol tipoRol;
    private List<Juego> juegosDisponibles;

    public Rol() {
        this("SIN_ROL");
    }

    public Rol(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public void agregarJuego(Juego j) {}
    public void eliminarJuego() {}
}

