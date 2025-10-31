package com.example.tpo.model.search;

import java.util.ArrayList;
import java.util.List;

import com.example.tpo.model.dominio.Scrim;

public class GestorBusquedas {
    private final List<IObservador> observadores = new ArrayList<>();
    private final List<Scrim> scrims = new ArrayList<>();

    public void suscribir(IObservador o) { observadores.add(o); }
    public void desuscribir(IObservador o) { observadores.remove(o); }
    public void agregarScrim(Scrim s) { scrims.add(s); actualizar(s); }
    public void actualizar(Scrim scrim) { for (IObservador o : observadores) o.actualizar(scrim); }
}

