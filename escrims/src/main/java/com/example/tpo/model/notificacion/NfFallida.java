package com.example.tpo.model.notificacion;

public class NfFallida implements INotificationState {
    @Override
    public void enviar(Notificacion n) { /* No-op. Usar reintentar(). */ }

    @Override
    public void cancelar(Notificacion n) { /* No-op. Ya fallida/cancelada. */ }

    @Override
    public void reintentar(Notificacion n) {
        IEstrategiaNotificacion canal = n.getCanal();
        try {
            if (canal != null) {
                canal.enviar(n);
                n.setEstado(new NfEnviada());
            } else {
                // permanece fallida
            }
        } catch (Exception ex) {
            // permanece fallida
        }
    }
}
