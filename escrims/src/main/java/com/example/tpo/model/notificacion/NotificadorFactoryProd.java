package com.example.tpo.model.notificacion;

public class NotificadorFactoryProd implements INotificadorFactory {
    @Override
    public Notificador crearNotificador() { return new Notificador(); }
}

