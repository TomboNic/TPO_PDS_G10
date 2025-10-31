package com.example.tpo.model.dominio;

import java.time.LocalDate;
import java.util.List;

public class Equipo {
    private int idEquipo;
    private String nombre;
    private LocalDate fechaCreacion;
    private List<Usuario> integrantes;

    public void agregarJugador(Usuario j) {}
    public void eliminarJugador(Usuario j) {}
}

