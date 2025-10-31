package com.example.tpo.model.dominio;

import java.time.Instant;
import java.util.ArrayList;
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

    public int getIdScrim() { return idScrim; }
    public void setIdScrim(int idScrim) { this.idScrim = idScrim; }
    public Juego getJuego() { return juego; }
    public void setJuego(Juego juego) { this.juego = juego; }
    public IFormato getFormato() { return formato; }
    public void setFormato(IFormato formato) { this.formato = formato; }
    public int getRangoMinimo() { return rangoMinimo; }
    public void setRangoMinimo(int rangoMinimo) { this.rangoMinimo = rangoMinimo; }
    public int getRangoMaximo() { return rangoMaximo; }
    public void setRangoMaximo(int rangoMaximo) { this.rangoMaximo = rangoMaximo; }
    public float getLatenciaMaxima() { return latenciaMaxima; }
    public void setLatenciaMaxima(float latenciaMaxima) { this.latenciaMaxima = latenciaMaxima; }
    public Region getRegion() { return region; }
    public void setRegion(Region region) { this.region = region; }
    public Instant getFechaHora() { return fechaHora; }
    public void setFechaHora(Instant fechaHora) { this.fechaHora = fechaHora; }
    public ScrimState getEstado() { return estado; }
    public void setEstado(ScrimState estado) { this.estado = estado; }
    public float getDuracionEstimada() { return duracionEstimada; }
    public void setDuracionEstimada(float duracionEstimada) { this.duracionEstimada = duracionEstimada; }
    public Usuario getCreador() { return creador; }
    public void setCreador(Usuario creador) { this.creador = creador; }
    public IModalidadScrim getModalidad() { return modalidad; }
    public void setModalidad(IModalidadScrim modalidad) { this.modalidad = modalidad; }
    public int getCupos() { return cupos; }
    public void setCupos(int cupos) { this.cupos = cupos; }
    public List<Rol> getReglasRoles() { return reglasRoles; }
    public void setReglasRoles(List<Rol> reglasRoles) { this.reglasRoles = reglasRoles; }
    public List<Usuario> getEquipo1() { if (equipo1 == null) equipo1 = new ArrayList<>(); return equipo1; }
    public void setEquipo1(List<Usuario> equipo1) { this.equipo1 = equipo1; }
    public List<Usuario> getEquipo2() { if (equipo2 == null) equipo2 = new ArrayList<>(); return equipo2; }
    public void setEquipo2(List<Usuario> equipo2) { this.equipo2 = equipo2; }
    public List<Usuario> getSuplentes() { if (suplentes == null) suplentes = new ArrayList<>(); return suplentes; }
    public void setSuplentes(List<Usuario> suplentes) { this.suplentes = suplentes; }

    public void postular(Usuario u, Rol rol) {}
    public void confirmar(Usuario u) {}
    public void cancelar() {}
    public void iniciar() {}
    public void finalizar(Resultado r) {}
}
