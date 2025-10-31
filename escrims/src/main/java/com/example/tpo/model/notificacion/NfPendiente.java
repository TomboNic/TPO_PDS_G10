package com.example.tpo.model.notificacion;

public class NfPendiente implements INotificationState {
    @Override
    public void enviar(Notificacion n) {}

    @Override
    public void cancelar(Notificacion n) {}

    @Override
    public void reintentar(Notificacion n) {}
}

