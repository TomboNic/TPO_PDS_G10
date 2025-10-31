package com.example.tpo.model.matchmaking;

import java.util.List;

import com.example.tpo.model.dominio.Busqueda;
import com.example.tpo.model.dominio.Usuario;

public class EstrategiaPorLatencia implements IEstrategiaEmparejamiento {
    private float latenciaMaxima;

    public float calcularDiferenciaLatencia(Usuario a, Usuario b) { return 0f; }

    @Override
    public List<Usuario> emparejar(Busqueda busqueda, List<Usuario> jugadoresDisponibles) { return jugadoresDisponibles; }
}

