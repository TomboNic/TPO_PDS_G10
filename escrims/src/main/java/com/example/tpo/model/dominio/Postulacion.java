package com.example.tpo.model.dominio;

import java.time.LocalDate;

import com.example.tpo.model.estado.moderacion.IEstado;

public class Postulacion {
    private int idPostulacion;
    private LocalDate fechaPostulacion;
    private IEstado estado;
    private Usuario postulante;
    private Scrim scrim;
    private Rol rolDeseado;

    public void aprobar() {}
    public void rechazar() {}
}

