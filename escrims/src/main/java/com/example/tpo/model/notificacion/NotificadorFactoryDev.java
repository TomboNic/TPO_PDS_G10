package com.example.tpo.model.notificacion;

public class NotificadorFactoryDev implements INotificadorFactory {
    @Override
    public Notificador crearNotificador() { return new Notificador(); }
}

