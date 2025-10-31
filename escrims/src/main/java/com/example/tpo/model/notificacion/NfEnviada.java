package com.example.tpo.model.notificacion;

public class NfEnviada implements INotificationState {
    @Override
    public void enviar(Notificacion n) {}

    @Override
    public void cancelar(Notificacion n) {}

    @Override
    public void reintentar(Notificacion n) {}
}

