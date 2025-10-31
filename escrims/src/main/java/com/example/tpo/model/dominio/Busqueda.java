package com.example.tpo.model.dominio;

import java.time.Instant;
import java.util.List;

import com.example.tpo.model.formato.IFormato;
import com.example.tpo.model.modalidad.IModalidadScrim;

public class Busqueda {
    private int idBusqueda;
    private Usuario usuario;
    private Juego juego;
    private IFormato formato;
    private int rangoMinimo;
    private int rangoMaximo;
    private float latenciaMaxima;
    private Region region;
    private Instant fechaHora;
    private boolean activa;
    private IModalidadScrim modalidad;

    public void desactivarBusqueda() {}
    public void guardarBusqueda() {}
    public void eliminarBusqueda() {}
    public void verificarCoincidencias(List<Scrim> scrims) {}
    public void crearAlerta() {}
}

