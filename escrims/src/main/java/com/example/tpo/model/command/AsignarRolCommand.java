package com.example.tpo.model.command;

import com.example.tpo.model.dominio.Rol;
import com.example.tpo.model.dominio.Scrim;
import com.example.tpo.model.dominio.Usuario;

public class AsignarRolCommand implements ICommandEquipo {
    private Usuario jugador;
    private Rol rolAnterior;
    private Rol rolNuevo;
    private Scrim scrim;

    @Override
    public void ejecutar() {}

    @Override
    public void undo() {}
}

