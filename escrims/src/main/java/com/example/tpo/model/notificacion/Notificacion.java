package com.example.tpo.model.notificacion;

import com.example.tpo.model.dominio.Usuario;

public class Notificacion {
    private String contenido;
    private Usuario destinatario;
    private int idNotificacion;
    private String tipoNotificacion;
    private IEstrategiaNotificacion canal;
    private INotificationState estado;
}

