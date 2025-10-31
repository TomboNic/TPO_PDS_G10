package com.example.tpo.model.state.scrim;

import com.example.tpo.model.dominio.Rol;
import com.example.tpo.model.dominio.Usuario;

public class LobbyArmado implements ScrimState {
    public void verificarConfirmaciones() {}

    @Override
    public void postular(ScrimContext ctx, Usuario u, Rol r) {
        if (ctx == null || ctx.getScrim() == null || u == null) return;
        // En lobby armado, nuevas postulaciones van como suplentes
        ctx.getScrim().getSuplentes().add(u);
    }

    @Override
    public void confirmar(ScrimContext ctx, Usuario u) { if (ctx != null) ctx.setState(new Confirmado()); }

    @Override
    public void iniciar(ScrimContext ctx) {}

    @Override
    public void finalizar(ScrimContext ctx) {}

    @Override
    public void cancelar(ScrimContext ctx) { if (ctx != null) ctx.setState(new Cancelado()); }

    @Override
    public String nombre() { return "LobbyArmado"; }
}
