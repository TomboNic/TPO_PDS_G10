package com.example.tpo.model.observer;

import com.example.tpo.model.notificacion.INotificadorFactory;
import com.example.tpo.model.notificacion.Notificador;
import com.example.tpo.model.notificacion.Notificacion;
import com.example.tpo.model.notificacion.Push;

public class NotificationSubscriber implements ISubscriber {
    private INotificadorFactory factory;

    @Override
    public void onEvent(IDomainEvent e) {
        if (factory == null || e == null) return;
        Notificador n = factory.crearNotificador();
        Notificacion notif = new Notificacion();
        notif.setTipoNotificacion(e.getClass().getSimpleName());
        notif.setContenido("Evento: " + e.getClass().getSimpleName());
        notif.setCanal(new Push());
        n.modificarEstrategia(new Push());
        n.enviar(notif);
    }

    public NotificationSubscriber() {}
    public NotificationSubscriber(INotificadorFactory factory) { this.factory = factory; }
    public void setFactory(INotificadorFactory factory) { this.factory = factory; }
}
