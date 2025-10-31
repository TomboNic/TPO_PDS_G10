package com.example.tpo.model.dominio;

import java.util.List;

import com.example.tpo.model.formato.IFormato; // referenced elsewhere
import com.example.tpo.model.matchmaking.IEstrategiaEmparejamiento;

public class Juego {
    private String nombre;
    private List<Rol> rolesDisponibles;
    private List<Region> regionesSoportadas;
    private List<Rango> rangosDisponibles;
    private IEstrategiaEmparejamiento reglasEmparejamiento;

    public void agregarRol(Rol r) {}
    public void agregaRango(Rango r) {}
    public void agregarRegion(Region r) {}
    public void eliminarRol() {}
    public void eliminarRango() {}
    public void eliminarRegion() {}
}

