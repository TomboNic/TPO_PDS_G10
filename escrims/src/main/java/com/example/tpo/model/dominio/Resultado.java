package com.example.tpo.model.dominio;

import java.time.Instant;
import java.util.List;

public class Resultado {
    private int idResultado;
    private Scrim scrim;
    private List<Usuario> ganadores;
    private Instant fecha;
    private List<EstadisticaJugador> estadisticasJugadores;
    private Usuario MVP;
    private Feedback feedback;

    public void registrarResultado(Scrim scrim, List<Usuario> ganadores) {}
    public void agregarFeedback(Feedback f) {}
    public void calcularMVP() {}
}

