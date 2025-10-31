package com.example.tpo.model.notificacion;

public class Notificador {
    private IEstrategiaNotificacion estrategia;

    public void enviar(Notificacion notif) { if (estrategia != null) estrategia.enviar(notif); }
    public void modificarEstrategia(IEstrategiaNotificacion est) { this.estrategia = est; }
}

