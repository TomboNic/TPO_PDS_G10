package com.example.tpo.model.matchmaking;

import java.util.List;

import com.example.tpo.model.dominio.Busqueda;
import com.example.tpo.model.dominio.Usuario;

public class GestorEmparejamiento {
    private IEstrategiaEmparejamiento estrategia;

    public void modificarEstrategia(IEstrategiaEmparejamiento e) { this.estrategia = e; }
    public void emparejar(Busqueda busqueda, List<Usuario> jugadoresDisponibles) {
        if (estrategia != null) {
            estrategia.emparejar(busqueda, jugadoresDisponibles);
        }
    }

    public List<Usuario> emparejarYDevolver(Busqueda busqueda, List<Usuario> jugadoresDisponibles) {
        if (estrategia == null) return jugadoresDisponibles;
        return estrategia.emparejar(busqueda, jugadoresDisponibles);
    }
}
