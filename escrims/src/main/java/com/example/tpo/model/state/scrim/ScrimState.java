package com.example.tpo.model.state.scrim;

import com.example.tpo.model.dominio.Rol;
import com.example.tpo.model.dominio.Usuario;

public interface ScrimState {
    void postular(ScrimContext ctx, Usuario u, Rol r);
    void confirmar(ScrimContext ctx, Usuario u);
    void iniciar(ScrimContext ctx);
    void finalizar(ScrimContext ctx);
    void cancelar(ScrimContext ctx);
    String nombre();
}

