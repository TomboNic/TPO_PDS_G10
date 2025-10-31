package com.example.tpo.model.notificacion;

public class Push implements IEstrategiaNotificacion {
    private IAdapterFirebase adapter;

    @Override
    public void enviar(Notificacion n) { if (adapter != null) adapter.enviar(n); }
}

