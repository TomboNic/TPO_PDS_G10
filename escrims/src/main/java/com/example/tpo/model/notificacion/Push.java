package com.example.tpo.model.notificacion;

public class Push implements IEstrategiaNotificacion {
    private IAdapterFirebase adapter;

    @Override
    public void enviar(Notificacion n) { if (adapter != null) adapter.enviar(n); }

    public void setAdapter(IAdapterFirebase adapter) { this.adapter = adapter; }
    public IAdapterFirebase getAdapter() { return adapter; }
}
