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
    public ScrimBuilder juego(Juego j) { return this; }
    public ScrimBuilder formato(IFormato f) { return this; }
    public ScrimBuilder region(Region r) { return this; }
    public ScrimBuilder rango(int min, int max) { return this; }
    public ScrimBuilder latenciaMax(float ms) { return this; }
    public ScrimBuilder fecha(Instant t) { return this; }
    public ScrimBuilder duracion(Duration d) { return this; }
    public ScrimBuilder cuposPorLado(int n) { return this; }
    public ScrimBuilder modalidad(IModalidadScrim m) { return this; }
    public Scrim build(DomainEventBus bus) { return new Scrim(); }
}

