package com.example.tpo.model.calendario;

import java.util.ArrayList;
import java.util.List;

import com.example.tpo.model.dominio.Scrim;

public class Calendario {
    private ICalendarAdapter adapter;
    private final List<Scrim> eventos = new ArrayList<>();

    public void crearEvento(Scrim scrim) { eventos.add(scrim); if (adapter != null) adapter.crearEvento(scrim); }
    public void eliminarEvento(Scrim scrim) { eventos.remove(scrim); if (adapter != null) adapter.eliminarEvento(scrim); }
    public void actualizarEvento(Scrim scrim) { if (adapter != null) adapter.actualizarEvento(scrim); }
    public void programarRecordatorio() {}
}

