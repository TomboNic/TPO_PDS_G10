package com.example.tpo.model.notificacion;

public interface INotificationState {
    void enviar(Notificacion n);
    void cancelar(Notificacion n);
    void reintentar(Notificacion n);
}

