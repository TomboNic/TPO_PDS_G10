package com.example.tpo.model.dominio;

import java.time.LocalDate;

public class Confirmacion {
    private int idConfirmacion;
    private LocalDate fechaConfirmacion;
    private Postulacion postulacion;
    private Usuario postulante;
    private Scrim scrim;
    private boolean confirmado;

    public void confirmar() { this.confirmado = true; }
}

