package com.example.tpo.model.dominio;

import java.util.List;

import com.example.tpo.model.tipo.ITipoRol;

public class Rol {
    private String nombre;
    private ITipoRol tipoRol;
    private List<Juego> juegosDisponibles;

    public void agregarJuego(Juego j) {}
    public void eliminarJuego() {}
}

