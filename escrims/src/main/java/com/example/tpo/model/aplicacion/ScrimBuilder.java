package com.example.tpo.model.aplicacion;

import java.time.Duration;
import java.time.Instant;

import com.example.tpo.model.dominio.Juego;
import com.example.tpo.model.dominio.Region;
import com.example.tpo.model.dominio.Scrim;
import com.example.tpo.model.formato.IFormato;
import com.example.tpo.model.modalidad.IModalidadScrim;
import com.example.tpo.model.observer.DomainEventBus;

public class ScrimBuilder {
    private Juego juego;
    private IFormato formato;
    private Region region;
    private int rangoMin;
    private int rangoMax;
    private float latenciaMax;
    private Instant fecha;
    private Duration duracion;
    private int cuposPorLado;
    private IModalidadScrim modalidad;

    public ScrimBuilder juego(Juego j) { this.juego = j; return this; }
    public ScrimBuilder formato(IFormato f) { this.formato = f; return this; }
    public ScrimBuilder region(Region r) { this.region = r; return this; }
    public ScrimBuilder rango(int min, int max) { this.rangoMin = min; this.rangoMax = max; return this; }
    public ScrimBuilder latenciaMax(float ms) { this.latenciaMax = ms; return this; }
    public ScrimBuilder fecha(Instant t) { this.fecha = t; return this; }
    public ScrimBuilder duracion(Duration d) { this.duracion = d; return this; }
    public ScrimBuilder cuposPorLado(int n) { this.cuposPorLado = n; return this; }
    public ScrimBuilder modalidad(IModalidadScrim m) { this.modalidad = m; return this; }

    public Scrim build(DomainEventBus bus) {
        Scrim s = new Scrim();
        s.setJuego(juego);
        s.setFormato(formato);
        s.setRegion(region);
        s.setRangoMinimo(rangoMin);
        s.setRangoMaximo(rangoMax);
        s.setLatenciaMaxima(latenciaMax);
        s.setFechaHora(fecha);
        s.setDuracionEstimada(duracion != null ? (float) duracion.toMinutes() : 0f);
        s.setModalidad(modalidad);
        s.setCupos(cuposPorLado > 0 ? cuposPorLado * 2 : 0);
        // Equipos/suplentes se inicializan lazy en getters
        if (bus != null) {
            bus.publish(new com.example.tpo.model.observer.events.ScrimCreated());
        }
        return s;
    }
}
