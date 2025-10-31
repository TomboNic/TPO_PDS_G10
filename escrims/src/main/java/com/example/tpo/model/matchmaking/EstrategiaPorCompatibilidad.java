package com.example.tpo.model.matchmaking;

import java.util.List;

import com.example.tpo.model.dominio.Busqueda;
import com.example.tpo.model.dominio.Usuario;

public class EstrategiaPorCompatibilidad implements IEstrategiaEmparejamiento {
    private float pesoRoles;
    private float pesoHistorial;
    private float pesoFairPlay;

    public boolean calcularCompatibilidad(Usuario a, Usuario b) { return true; }

    @Override
    public List<Usuario> emparejar(Busqueda busqueda, List<Usuario> jugadoresDisponibles) { return jugadoresDisponibles; }
}

