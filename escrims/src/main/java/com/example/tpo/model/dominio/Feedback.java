package com.example.tpo.model.dominio;

import java.time.Instant;

import com.example.tpo.model.estado.moderacion.IEstado;

public class Feedback {
    private int idFeedback;
    private String descripcion;
    private Usuario usuario;
    private IEstado estadoModeracion;
    private Instant fecha;
    private int rating;

    public void registrarFeedback(Usuario autor, String comentario, int rating) {}
    public void editarComentario(String texto) {}
}

