package com.example.tpo.model.notificacion;

public class NfPendiente implements INotificationState {
    @Override
    public void enviar(Notificacion n) {
        IEstrategiaNotificacion canal = n.getCanal();
        try {
            if (canal != null) {
                canal.enviar(n);
                n.setEstado(new NfEnviada());
            } else {
                n.setEstado(new NfFallida());
            }
        } catch (Exception ex) {
            n.setEstado(new NfFallida());
        }
    }

    @Override
    public void cancelar(Notificacion n) { n.setEstado(new NfFallida()); }

    @Override
    public void reintentar(Notificacion n) { enviar(n); }
}
