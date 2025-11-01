package com.example.tpo.model.state.scrim;

import com.example.tpo.model.dominio.Rol;
import com.example.tpo.model.dominio.Usuario;

public class BuscandoJugadores implements ScrimState {
    public void verificarCupoCompleto() {}

    @Override
    public void postular(ScrimContext ctx, Usuario u, Rol r) {
        if (ctx == null || ctx.getScrim() == null || u == null) return;
        var s = ctx.getScrim();
        int maxPorLado = Math.max(1, s.getCupos() / 2);
        
        // Verificar si el usuario ya está en algún equipo
        if (s.getEquipo1().contains(u) || s.getEquipo2().contains(u) || s.getSuplentes().contains(u)) {
            return; // Usuario ya está postulado
        }

        // Distribuir equitativamente entre equipos
        if (s.getEquipo1().size() <= s.getEquipo2().size() && s.getEquipo1().size() < maxPorLado) {
            s.getEquipo1().add(u);
        } else if (s.getEquipo2().size() < maxPorLado) {
            s.getEquipo2().add(u);
        } else {
            s.getSuplentes().add(u);
        }

        if (s.getEquipo1().size() == maxPorLado && s.getEquipo2().size() == maxPorLado) {
            ctx.setState(new LobbyArmado());
        }
    }

    @Override
    public void confirmar(ScrimContext ctx, Usuario u) {}

    @Override
    public void iniciar(ScrimContext ctx) {}

    @Override
    public void finalizar(ScrimContext ctx) {}

    @Override
    public void cancelar(ScrimContext ctx) { if (ctx != null) ctx.setState(new Cancelado()); }

    @Override
    public String nombre() { return "BuscandoJugadores"; }
}
