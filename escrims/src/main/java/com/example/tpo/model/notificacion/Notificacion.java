package com.example.tpo.model.notificacion;

import com.example.tpo.model.dominio.Usuario;

public class Notificacion {
    private String contenido;
    private Usuario destinatario;
    private int idNotificacion;
    private String tipoNotificacion;
    private IEstrategiaNotificacion canal;
    private INotificationState estado;

    public Notificacion() {
        this.estado = new NfPendiente();
    }

    public Notificacion(String contenido,
                         Usuario destinatario,
                         int idNotificacion,
                         String tipoNotificacion,
                         IEstrategiaNotificacion canal) {
        this.contenido = contenido;
        this.destinatario = destinatario;
        this.idNotificacion = idNotificacion;
        this.tipoNotificacion = tipoNotificacion;
        this.canal = canal;
        this.estado = new NfPendiente();
    }

    public void enviar() { if (estado != null) estado.enviar(this); }
    public void cancelar() { if (estado != null) estado.cancelar(this); }
    public void reintentar() { if (estado != null) estado.reintentar(this); }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public Usuario getDestinatario() { return destinatario; }
    public void setDestinatario(Usuario destinatario) { this.destinatario = destinatario; }

    public int getIdNotificacion() { return idNotificacion; }
    public void setIdNotificacion(int idNotificacion) { this.idNotificacion = idNotificacion; }

    public String getTipoNotificacion() { return tipoNotificacion; }
    public void setTipoNotificacion(String tipoNotificacion) { this.tipoNotificacion = tipoNotificacion; }

    public IEstrategiaNotificacion getCanal() { return canal; }
    public void setCanal(IEstrategiaNotificacion canal) { this.canal = canal; }

    public INotificationState getEstado() { return estado; }
    public void setEstado(INotificationState estado) { this.estado = estado; }
}
