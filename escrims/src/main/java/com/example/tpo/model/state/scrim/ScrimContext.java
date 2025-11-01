package com.example.tpo.model.state.scrim;

import com.example.tpo.model.dominio.Rol;
import com.example.tpo.model.dominio.Scrim;
import com.example.tpo.model.dominio.Usuario;
import com.example.tpo.model.observer.DomainEventBus;
import com.example.tpo.model.observer.events.LobbyCompleted;
import com.example.tpo.model.observer.events.ScrimFinalized;
import com.example.tpo.model.observer.events.ScrimStateChanged;

public class ScrimContext {
    private ScrimState state;
    private Scrim scrim;
    private DomainEventBus bus;

    public void setState(ScrimState s) {
        if (s == null) return;
        // Evitar re-emitir eventos si el estado es del mismo tipo
        if (this.state != null && this.state.getClass().equals(s.getClass())) {
            return;
        }

        String from = this.state != null ? this.state.nombre() : null;
        this.state = s;

        if (bus != null) {
            // Evento genérico de cambio de estado con datos
            bus.publish(new ScrimStateChanged(from, s.nombre(), scrim != null ? scrim.getIdScrim() : 0));
            // Eventos específicos con datos
            if (s instanceof LobbyArmado) bus.publish(new LobbyCompleted(scrim != null ? scrim.getIdScrim() : 0));
            if (s instanceof Finalizado) bus.publish(new ScrimFinalized(scrim != null ? scrim.getIdScrim() : 0));
        }
    }

    public ScrimState getState() { return state; }
    public void postular(Usuario u, Rol r) { if (state != null) state.postular(this, u, r); }
    public void confirmar(Usuario u) { if (state != null) state.confirmar(this, u); }
    public void iniciar() { if (state != null) state.iniciar(this); }
    public void finalizar() { if (state != null) state.finalizar(this); }
    public void cancelar() { if (state != null) state.cancelar(this); }

    public Scrim getScrim() { return scrim; }
    public void setScrim(Scrim scrim) { this.scrim = scrim; }

    public DomainEventBus getBus() { return bus; }
    public void setBus(DomainEventBus bus) { this.bus = bus; }
}
