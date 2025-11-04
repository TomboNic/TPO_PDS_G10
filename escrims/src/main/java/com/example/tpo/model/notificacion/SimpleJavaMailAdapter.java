package com.example.tpo.model.notificacion;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Adaptador mínimo para enviar emails con JavaMail (SMTP).
 * Lee configuración desde variables de entorno:
 *  - SMTP_HOST (obligatorio)
 *  - SMTP_PORT (opcional, default 587)
 *  - SMTP_USER (usuario SMTP)
 *  - SMTP_PASS (password o app password)
 *  - SMTP_FROM (opcional, default = SMTP_USER)
 *  - SMTP_TLS  (opcional, "true" para STARTTLS)
 */
public class SimpleJavaMailAdapter implements IAdapterJavaMail {
    private final String host;
    private final int port;
    private final String user;
    private final String pass;
    private final String from;
    private final boolean useTLS;

    public SimpleJavaMailAdapter() {
        MailConfig cfg = MailConfig.load();
        this.host = cfg.host();
        this.port = cfg.port();
        this.user = cfg.user();
        this.pass = cfg.pass();
        this.from = cfg.from();
        this.useTLS = cfg.tls();
    }

    @Override
    public void enviar(Notificacion n) {
        try {
            if (host == null || host.isBlank()) {
                System.out.println("[Email] SMTP_HOST no configurado; omitiendo envio.");
                return;
            }
            if (n == null || n.getDestinatario() == null || n.getDestinatario().getEmail() == null || n.getDestinatario().getEmail().isBlank()) {
                System.out.println("[Email] Notificacion sin destinatario email; omitiendo envio.");
                return;
            }

            Properties props = new Properties();
            props.put("mail.smtp.auth", !user.isBlank());
            props.put("mail.smtp.starttls.enable", useTLS);
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", String.valueOf(port));

            Session session;
            if (!user.isBlank()) {
                session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, pass);
                    }
                });
            } else {
                session = Session.getInstance(props);
            }

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from != null && !from.isBlank() ? from : user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(n.getDestinatario().getEmail()));
            message.setSubject(n.getTipoNotificacion() != null ? n.getTipoNotificacion() : "Notificacion");
            message.setText(n.getContenido() != null ? n.getContenido() : "");

            Transport.send(message);
            System.out.println("[Email] Enviado a " + n.getDestinatario().getEmail());
        } catch (Exception ex) {
            System.out.println("[Email] Error enviando: " + ex.getMessage());
        }
    }
}
