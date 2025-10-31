package com.example.tpo.model.observer;

import com.example.tpo.model.notificacion.INotificadorFactory;

public class NotificationSubscriber implements ISubscriber {
    private INotificadorFactory factory;

    @Override
    public void onEvent(IDomainEvent e) {}
}

