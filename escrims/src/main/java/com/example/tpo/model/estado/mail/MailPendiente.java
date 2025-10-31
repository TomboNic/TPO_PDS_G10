package com.example.tpo.model.estado.mail;

import com.example.tpo.model.dominio.Usuario;

public class MailPendiente implements IEstadoMail {
    @Override
    public void verificarMail(Usuario u) {}

    @Override
    public void reenviarMail(Usuario u) {}
}

