package com.example.tpo.model.observer;
import com.example.tpo.model.notificacion.INotificadorFactory;
import com.example.tpo.model.notificacion.Notificador;
import com.example.tpo.model.notificacion.Notificacion;
import com.example.tpo.model.notificacion.NotificadorFactoryDev;
import com.example.tpo.model.notificacion.Push;
import com.example.tpo.model.observer.events.LobbyCompleted;
import com.example.tpo.model.observer.events.ScrimFinalized;
import com.example.tpo.model.observer.events.ScrimStateChanged;
import com.example.tpo.views.ViewFiles;

public class NotificationSubscriber implements ISubscriber {
    private INotificadorFactory factory;

    @Override
    public void onEvent(IDomainEvent e) {
        if (factory == null || e == null) return;
        Notificador n = factory.crearNotificador();

        // Armar notificación con contenido enriquecido
        Notificacion notif = new Notificacion();
        notif.setTipoNotificacion(e.getClass().getSimpleName());
        String contenido = "Evento: " + e.getClass().getSimpleName();
        if (e instanceof ScrimStateChanged sc) {
            contenido = "Estado: " + (sc.getFrom() != null ? sc.getFrom() : "(init)") + " -> " + sc.getTo() + " (scrim=" + sc.getScrimId() + ")";
        } else if (e instanceof LobbyCompleted lc) {
            contenido = "Lobby completo (scrim=" + lc.getScrimId() + ")";
        } else if (e instanceof ScrimFinalized sf) {
            contenido = "Scrim finalizado (scrim=" + sf.getScrimId() + ")";
        }
        notif.setContenido(contenido);

        // Canal push; en DEV imprimimos directamente para evitar adapter concreto
        Push push = new Push();
        notif.setCanal(push);
        n.modificarEstrategia(push);

        if (factory instanceof NotificadorFactoryDev) {
            String tipo = notif.getTipoNotificacion();
            String contenidoLog = notif.getContenido();
            String destinatario = (notif.getDestinatario() != null)
                    ? (notif.getDestinatario().getNombreUsuario() != null ? notif.getDestinatario().getNombreUsuario() : notif.getDestinatario().toString())
                    : "(broadcast)";
            ViewFiles.println("notifications", "[Push][DEV] tipo=" + tipo + ", to=" + destinatario + ", msg=" + contenidoLog);
        } else {
            n.enviar(notif);
        }
    }

    public NotificationSubscriber() {}
    public NotificationSubscriber(INotificadorFactory factory) { this.factory = factory; }
    public void setFactory(INotificadorFactory factory) { this.factory = factory; }
}
