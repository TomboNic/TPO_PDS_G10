# TPO_PDS_G10

Proyecto de consola para gestionar scrims con vistas simples, notificaciones y flujo de casos de uso.

- Requisitos: Java 17 y Maven.
- Build: `cd escrims && mvn -DskipTests package`
- Run: `java -jar target/escrims-1.0-SNAPSHOT.jar` (si hay empaquetado de JAR)

## Configuración de correo (SMTP)

1. Copiar `escrims/src/main/resources/mail.properties` a `config/mail.properties` y completar:
   - `SMTP_HOST`, `SMTP_PORT`, `SMTP_TLS`, `SMTP_USER`, `SMTP_PASS`, `SMTP_FROM`, `SMTP_TO`
2. Gmail requiere App Password (no la contraseña normal) con 2FA.
3. Las notificaciones se envían por email si `SMTP_HOST` está configurado; sino, se registran en `views/notifications.log`.

## Consejos de uso

- Menú principal con casos de uso; varias salidas usan vistas mejoradas por consola.
- La opción "Notificar Eventos" permite enviar a los participantes de un scrim ingresando su ID.
- Los archivos `.log` se guardan en `views/` (ignorados por git).
