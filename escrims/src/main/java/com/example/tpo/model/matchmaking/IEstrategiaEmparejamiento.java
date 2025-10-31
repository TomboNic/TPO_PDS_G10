package com.example.tpo.model.matchmaking;

import java.util.List;

import com.example.tpo.model.dominio.Busqueda;
import com.example.tpo.model.dominio.Usuario;

public interface IEstrategiaEmparejamiento {
    List<Usuario> emparejar(Busqueda busqueda, List<Usuario> jugadoresDisponibles);
}

