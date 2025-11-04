package com.example.tpo.model.notificacion;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Carga configuración SMTP sin depender de variables de entorno.
 * Orden de carga y override (de menor a mayor prioridad):
 *  1) Defaults internos
 *  2) Classpath: src/main/resources/mail.properties
 *  3) Archivo externo: ./config/mail.properties (si existe)
 *  4) System properties (-DSMTP_HOST=...)
 *  5) Variables de entorno (como último recurso)
 */
public final class MailConfig {
    public static final String K_HOST = "SMTP_HOST";
    public static final String K_PORT = "SMTP_PORT";
    public static final String K_USER = "SMTP_USER";
    public static final String K_PASS = "SMTP_PASS";
    public static final String K_FROM = "SMTP_FROM";
    public static final String K_TLS  = "SMTP_TLS";
    public static final String K_TO   = "SMTP_TO"; // destinatario por defecto opcional

    private final Properties props;

    private MailConfig(Properties props) { this.props = props; }

    public static MailConfig load() {
        Properties p = new Properties();
        // Defaults
        p.setProperty(K_PORT, "587");
        p.setProperty(K_TLS, "true");

        // 2) Classpath resource
        try (InputStream in = MailConfig.class.getClassLoader().getResourceAsStream("mail.properties")) {
            if (in != null) p.load(in);
        } catch (Exception ignored) {}

        // 3) External file ./config/mail.properties
        File external = new File("config", "mail.properties");
        if (external.exists()) {
            try (InputStream in = new FileInputStream(external)) { p.load(in); } catch (Exception ignored) {}
        }

        // 4) System properties override
        overrideFromSystemProperties(p, K_HOST, K_PORT, K_USER, K_PASS, K_FROM, K_TLS, K_TO);

        // 5) Env vars override (opcional)
        overrideFromEnv(p, K_HOST, K_PORT, K_USER, K_PASS, K_FROM, K_TLS, K_TO);

        return new MailConfig(p);
    }

    private static void overrideFromSystemProperties(Properties p, String... keys) {
        for (String k : keys) {
            String v = System.getProperty(k);
            if (v != null) p.setProperty(k, v);
        }
    }

    private static void overrideFromEnv(Properties p, String... keys) {
        for (String k : keys) {
            String v = System.getenv(k);
            if (v != null) p.setProperty(k, v);
        }
    }

    public String get(String key, String def) {
        String v = props.getProperty(key);
        return v != null ? v : def;
    }

    public String host() { return get(K_HOST, null); }
    public int port() {
        try { return Integer.parseInt(get(K_PORT, "587")); } catch (NumberFormatException e) { return 587; }
    }
    public String user() { return get(K_USER, ""); }
    public String pass() { return get(K_PASS, ""); }
    public String from() {
        String f = get(K_FROM, "");
        if (f == null || f.isBlank()) return user();
        return f;
    }
    public boolean tls() { return Boolean.parseBoolean(get(K_TLS, "true")); }
    public String toEmail() { return get(K_TO, ""); }
}
