package com.example.tpo.model.calendario;

import com.example.tpo.model.dominio.Scrim;

public interface ICalendarAdapter {
    void crearEvento(Scrim scrim);
    void eliminarEvento(Scrim scrim);
    void actualizarEvento(Scrim scrim);
}

