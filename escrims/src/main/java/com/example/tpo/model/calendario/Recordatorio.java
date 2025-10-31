package com.example.tpo.model.calendario;

import java.time.LocalDateTime;

import com.example.tpo.model.dominio.Scrim;
import com.example.tpo.model.dominio.Usuario;

public class Recordatorio {
    private LocalDateTime fechaHoraEvento;
    private int horasAntes;
    private String mensaje;
    private Usuario usuario;

    public void activarNotificacion() {}
}

