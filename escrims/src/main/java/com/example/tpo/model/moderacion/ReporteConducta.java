package com.example.tpo.model.moderacion;

import java.time.Instant;

import com.example.tpo.model.dominio.Scrim;
import com.example.tpo.model.dominio.Usuario;
import com.example.tpo.model.estado.moderacion.IEstado;
import com.example.tpo.model.tipo.ITipoPenalidad;

public class ReporteConducta {
    private int idReporte;
    private Usuario reportante;
    private Usuario denunciado;
    private ITipoPenalidad motivo;
    private String descripcion;
    private Instant fecha;
    private IEstado estado;
    private Scrim scrim;
    private String sancion;

    public void resolver() {}
}

