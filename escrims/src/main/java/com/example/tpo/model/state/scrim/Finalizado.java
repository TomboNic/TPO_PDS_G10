package com.example.tpo.model.state.scrim;

import com.example.tpo.model.dominio.Feedback;
import com.example.tpo.model.dominio.Resultado;
import com.example.tpo.model.dominio.Rol;
import com.example.tpo.model.dominio.Usuario;

public class Finalizado implements ScrimState {
    public Resultado registrarResultado() { return null; }
    public Feedback registrarFeedback() { return null; }

    @Override
    public void postular(ScrimContext ctx, Usuario u, Rol r) {}

    @Override
    public void confirmar(ScrimContext ctx, Usuario u) {}

    @Override
    public void iniciar(ScrimContext ctx) {}

    @Override
    public void finalizar(ScrimContext ctx) {}

    @Override
    public void cancelar(ScrimContext ctx) {}

    @Override
    public String nombre() { return "Finalizado"; }
}
