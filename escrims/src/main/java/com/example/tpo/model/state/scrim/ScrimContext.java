package com.example.tpo.model.state.scrim;

import com.example.tpo.model.dominio.Rol;
import com.example.tpo.model.dominio.Scrim;
import com.example.tpo.model.dominio.Usuario;

public class ScrimContext {
    private ScrimState state;
    private Scrim scrim;

    public void setState(ScrimState s) { this.state = s; }
    public ScrimState getState() { return state; }
    public void postular(Usuario u, Rol r) { if (state != null) state.postular(this, u, r); }
    public void confirmar(Usuario u) { if (state != null) state.confirmar(this, u); }
    public void iniciar() { if (state != null) state.iniciar(this); }
    public void finalizar() { if (state != null) state.finalizar(this); }
    public void cancelar() { if (state != null) state.cancelar(this); }

    public Scrim getScrim() { return scrim; }
    public void setScrim(Scrim scrim) { this.scrim = scrim; }
}
