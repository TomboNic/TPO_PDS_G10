package com.example.tpo.model.notificacion;

public class NotificadorPorRegion implements INotificadorFactory {
    @Override
    public Notificador crearNotificador() { return new Notificador(); }
}

