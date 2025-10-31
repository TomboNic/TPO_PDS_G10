package com.example.tpo.model.dominio;

import java.time.Instant;
import java.util.List;

import com.example.tpo.model.formato.IFormato;
import com.example.tpo.model.modalidad.IModalidadScrim;
import com.example.tpo.model.state.scrim.ScrimState;

public class Scrim {
    private int idScrim;
    private Juego juego;
    private IFormato formato;
    private int rangoMinimo;
    private int rangoMaximo;
    private float latenciaMaxima;
    private Region region;
    private Instant fechaHora;
    private ScrimState estado;
    private float duracionEstimada;
    private Usuario creador;
    private IModalidadScrim modalidad;
    private int cupos;
    private List<Rol> reglasRoles;
    private List<Usuario> equipo1;
    private List<Usuario> equipo2;
    private List<Usuario> suplentes;

    public void postular(Usuario u, Rol rol) {}
    public void confirmar(Usuario u) {}
    public void cancelar() {}
    public void iniciar() {}
    public void finalizar(Resultado r) {}
}

