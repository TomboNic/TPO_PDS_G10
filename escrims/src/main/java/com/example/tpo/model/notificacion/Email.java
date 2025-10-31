package com.example.tpo.model.notificacion;

public class Email implements IEstrategiaNotificacion {
    private IAdapterJavaMail adapter;

    @Override
    public void enviar(Notificacion n) { if (adapter != null) adapter.enviar(n); }

    public void setAdapter(IAdapterJavaMail adapter) { this.adapter = adapter; }
    public IAdapterJavaMail getAdapter() { return adapter; }
}
