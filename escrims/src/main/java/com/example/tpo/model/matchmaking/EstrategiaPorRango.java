package com.example.tpo.model.matchmaking;

import java.util.List;

import com.example.tpo.model.dominio.Busqueda;
import com.example.tpo.model.dominio.Usuario;

public class EstrategiaPorRango implements IEstrategiaEmparejamiento {
    private int diferenciaMaxima;

    public int calcularDiferenciaRango(Usuario a, Usuario b) { return 0; }

    @Override
    public List<Usuario> emparejar(Busqueda busqueda, List<Usuario> jugadoresDisponibles) { return jugadoresDisponibles; }
}

