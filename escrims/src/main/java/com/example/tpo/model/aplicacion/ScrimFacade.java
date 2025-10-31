package com.example.tpo.model.aplicacion;

import com.example.tpo.model.dominio.Resultado;
import com.example.tpo.model.dominio.Rol;
import com.example.tpo.model.dominio.Scrim;
import com.example.tpo.model.dominio.Usuario;
import com.example.tpo.model.observer.DomainEventBus;
import com.example.tpo.model.state.scrim.ScrimContext;

public class ScrimFacade {
    public int crearScrim() { return 0; }
    public void postular(int id, Usuario u, Rol r) {}
    public void confirmar(int id, Usuario u) {}
    public void iniciar(int id) {}
    public void finalizar(int id, Resultado res) {}
    public void cancelar(int id) {}
    public void armarLobby(int id) {}
}

