package com.example.tpo.model.command;

import com.example.tpo.model.dominio.Scrim;
import com.example.tpo.model.dominio.Usuario;

public class ReemplazarJugadorCommand implements ICommandEquipo {
    private Usuario jugadorSaliente;
    private Usuario jugadorEntrante;
    private Scrim scrim;

    @Override
    public void ejecutar() {}

    @Override
    public void undo() {}
}

