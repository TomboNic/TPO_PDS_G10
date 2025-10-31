package com.example.tpo.model.estado.mail;

import com.example.tpo.model.dominio.Usuario;

public interface IEstadoMail {
    void verificarMail(Usuario u);
    void reenviarMail(Usuario u);
}

