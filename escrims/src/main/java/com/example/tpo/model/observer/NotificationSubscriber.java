package com.example.tpo.model.observer;
import com.example.tpo.model.notificacion.INotificadorFactory;
import com.example.tpo.model.notificacion.Notificador;
import com.example.tpo.model.notificacion.Notificacion;
import com.example.tpo.model.notificacion.NotificadorFactoryDev;
import com.example.tpo.model.notificacion.Push;
import com.example.tpo.model.notificacion.MailConfig;
import com.example.tpo.model.notificacion.Email;
import com.example.tpo.model.notificacion.SimpleJavaMailAdapter;
import com.example.tpo.model.dominio.Usuario;
import com.example.tpo.model.aplicacion.ScrimFacade;
import com.example.tpo.model.dominio.Scrim;
import com.example.tpo.model.observer.events.LobbyCompleted;
import com.example.tpo.model.observer.events.ScrimFinalized;
import com.example.tpo.model.observer.events.ScrimStateChanged;
import com.example.tpo.views.ViewFiles;

public class NotificationSubscriber implements ISubscriber {
    private INotificadorFactory factory;
    private ScrimFacade facade; // opcional: para enriquecer y obtener destinatarios

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
            String extra = rosterResumen(sf.getScrimId());
            contenido = "Scrim finalizado (scrim=" + sf.getScrimId() + ")" + (extra.isEmpty() ? "" : "\n" + extra);
        }
        notif.setContenido(contenido);

        // Preferir email si hay configuracion SMTP valida
        MailConfig cfg = MailConfig.load();
        boolean canEmail = cfg.host() != null && !cfg.host().isBlank();
        // Resolver destinatarios: participantes del scrim (si hay) o SMTP_TO (admite coma)
        String toEmail = resolveRecipientsCsv(e, cfg);
        if (canEmail && (notif.getDestinatario() == null && toEmail != null && !toEmail.isBlank())) {
            Usuario u = new Usuario();
            u.setNombreUsuario("mail-to");
            u.setEmail(toEmail); // puede ser CSV; JavaMail parsea múltiples
            notif.setDestinatario(u);
        }

        if (canEmail && notif.getDestinatario() != null && notif.getDestinatario().getEmail() != null && !notif.getDestinatario().getEmail().isBlank()) {
            Email email = new Email();
            email.setAdapter(new SimpleJavaMailAdapter());
            notif.setCanal(email);
            n.modificarEstrategia(email);
        } else {
            // Canal push; en DEV imprimimos directamente
            Push push = new Push();
            notif.setCanal(push);
            n.modificarEstrategia(push);
        }

        if (factory instanceof NotificadorFactoryDev) {
            String tipo = notif.getTipoNotificacion();
            String contenidoLog = notif.getContenido();
            String destinatario = (notif.getDestinatario() != null)
                    ? (notif.getDestinatario().getNombreUsuario() != null ? notif.getDestinatario().getNombreUsuario() : (notif.getDestinatario().getEmail() != null ? notif.getDestinatario().getEmail() : notif.getDestinatario().toString()))
                    : "(broadcast)";
            ViewFiles.println("notifications", "[Push][DEV] tipo=" + tipo + ", to=" + destinatario + ", msg=" + contenidoLog);
        }
        // Enviar por el canal seleccionado si existe
        n.enviar(notif);
    }

    public NotificationSubscriber() {}
    public NotificationSubscriber(INotificadorFactory factory) { this.factory = factory; }
    public void setFactory(INotificadorFactory factory) { this.factory = factory; }
    public void setFacade(ScrimFacade facade) { this.facade = facade; }

    private String resolveRecipientsCsv(IDomainEvent e, MailConfig cfg) {
        StringBuilder sb = new StringBuilder();
        int scrimId = -1;
        if (e instanceof ScrimStateChanged sc) scrimId = sc.getScrimId();
        if (e instanceof LobbyCompleted lc) scrimId = lc.getScrimId();
        if (e instanceof ScrimFinalized sf) scrimId = sf.getScrimId();
        if (scrimId > 0 && facade != null) {
            Scrim s = facade.getScrim(scrimId);
            java.util.LinkedHashSet<String> set = new java.util.LinkedHashSet<>();
            if (s != null) {
                s.getEquipo1().forEach(u -> addEmail(set, u));
                s.getEquipo2().forEach(u -> addEmail(set, u));
                s.getSuplentes().forEach(u -> addEmail(set, u));
            }
            for (String em : set) { if (!em.isBlank()) { if (sb.length() > 0) sb.append(','); sb.append(em); } }
        }
        // fallback a SMTP_TO
        if (sb.length() == 0) {
            String to = cfg.toEmail();
            if (to != null && !to.isBlank()) return to;
        }
        return sb.toString();
    }

    private void addEmail(java.util.Set<String> set, Usuario u) {
        if (u != null && u.getEmail() != null && !u.getEmail().isBlank()) set.add(u.getEmail());
    }

    private String rosterResumen(int scrimId) {
        if (facade == null) return "";
        Scrim s = facade.getScrim(scrimId);
        if (s == null) return "";
        String e1 = String.join(", ", s.getEquipo1().stream().map(this::nameOf).toList());
        String e2 = String.join(", ", s.getEquipo2().stream().map(this::nameOf).toList());
        return "Equipo 1: " + (e1.isBlank() ? "-" : e1) + "\n" + "Equipo 2: " + (e2.isBlank() ? "-" : e2);
    }

    private String nameOf(Usuario u) {
        if (u == null) return "?";
        return u.getNombreUsuario() != null ? u.getNombreUsuario() : (u.getEmail() != null ? u.getEmail() : u.toString());
    }
}
