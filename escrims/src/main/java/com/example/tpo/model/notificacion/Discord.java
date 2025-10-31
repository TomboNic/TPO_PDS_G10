package com.example.tpo.model.notificacion;

public class Discord implements IEstrategiaNotificacion {
    private IAdapterWebhook adapter;

    @Override
    public void enviar(Notificacion n) { if (adapter != null) adapter.enviar(n); }
}

