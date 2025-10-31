package com.example.tpo.model.dominio;

import java.time.Instant;

import com.example.tpo.model.tipo.ITipoPenalidad;

public class Penalidad {
    private int idPenalidad;
    private Usuario usuario;
    private String descripcion;
    private Instant fecha;
    private ITipoPenalidad tipo;
    private int duracion;

    public void registrarPenalidad(Usuario u, String desc) {}
    public void eliminarPenalidad() {}
    public void bloquearTemporariamenteUsuario() {}
}

