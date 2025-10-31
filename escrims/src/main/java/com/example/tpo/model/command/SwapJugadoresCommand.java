package com.example.tpo.model.command;

import com.example.tpo.model.dominio.Scrim;
import com.example.tpo.model.dominio.Usuario;

public class SwapJugadoresCommand implements ICommandEquipo {
    private Usuario jugador1;
    private Usuario jugador2;
    private Scrim scrim;

    @Override
    public void ejecutar() {}

    @Override
    public void undo() {}
}

