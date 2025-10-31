package com.example.tpo.model.command;

import java.util.ArrayList;
import java.util.List;

public class GestorEquipos {
    private final List<ICommandEquipo> historialComandos = new ArrayList<>();

    public void ejecutar(ICommandEquipo c) { historialComandos.add(c); c.ejecutar(); }
    public void undo() { if (!historialComandos.isEmpty()) historialComandos.remove(historialComandos.size()-1).undo(); }
}

