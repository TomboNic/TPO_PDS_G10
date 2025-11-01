package com.example.tpo.model.aplicacion;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import com.example.tpo.model.dominio.Resultado;
import com.example.tpo.model.dominio.Rol;
import com.example.tpo.model.dominio.Scrim;
import com.example.tpo.model.dominio.Usuario;
import com.example.tpo.model.observer.DomainEventBus;
import com.example.tpo.model.observer.ISubscriber;
import com.example.tpo.model.state.scrim.BuscandoJugadores;
import com.example.tpo.model.state.scrim.ScrimContext;
import com.example.tpo.model.state.scrim.Confirmado;
import com.example.tpo.model.state.scrim.EnJuego;
import com.example.tpo.model.state.scrim.Finalizado;
import com.example.tpo.model.state.scrim.Cancelado;
import com.example.tpo.model.state.scrim.LobbyArmado;

public class ScrimFacade {
    private final Map<Integer, ScrimContext> scrims = new HashMap<>();
    private final DomainEventBus bus = new DomainEventBus();
    private int seq = 1;

    public int crearScrim(int cuposPorLado) {
        Scrim s = new ScrimBuilder()
                .fecha(Instant.now())
                .cuposPorLado(cuposPorLado)
                .build(bus);
        s.setIdScrim(seq);
        ScrimContext ctx = new ScrimContext();
        ctx.setScrim(s);
        ctx.setState(new BuscandoJugadores());
        scrims.put(seq, ctx);
        return seq++;
    }

    public void postular(int id, Usuario u, Rol r) {
        ScrimContext ctx = scrims.get(id);
        if (ctx != null) ctx.postular(u, r);
    }

    public void confirmar(int id, Usuario u) {
        ScrimContext ctx = scrims.get(id);
        if (ctx != null) {
            ctx.confirmar(u);
            ctx.setState(new Confirmado());
        }
    }

    public void iniciar(int id) {
        ScrimContext ctx = scrims.get(id);
        if (ctx != null) {
            ctx.iniciar();
            ctx.setState(new EnJuego());
        }
    }

    public void finalizar(int id, Resultado res) {
        ScrimContext ctx = scrims.get(id);
        if (ctx != null) {
            ctx.finalizar();
            ctx.setState(new Finalizado());
        }
    }

    public void cancelar(int id) {
        ScrimContext ctx = scrims.get(id);
        if (ctx != null) {
            ctx.cancelar();
            ctx.setState(new Cancelado());
        }
    }

    public void armarLobby(int id) {
        ScrimContext ctx = scrims.get(id);
        if (ctx != null) ctx.setState(new LobbyArmado());
    }

    public Scrim getScrim(int id) {
        ScrimContext ctx = scrims.get(id);
        return ctx != null ? ctx.getScrim() : null;
    }

    public ScrimContext getContext(int id) { return scrims.get(id); }

    public void subscribe(ISubscriber s) { if (s != null) bus.subscribe(s); }
}
