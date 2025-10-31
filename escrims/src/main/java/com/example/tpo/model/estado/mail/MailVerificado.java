package com.example.tpo.model.estado.mail;

import com.example.tpo.model.dominio.Usuario;

public class MailVerificado implements IEstadoMail {
    @Override
    public void verificarMail(Usuario u) { /* ya verificado */ }

    @Override
    public void reenviarMail(Usuario u) { /* no aplica */ }
}
