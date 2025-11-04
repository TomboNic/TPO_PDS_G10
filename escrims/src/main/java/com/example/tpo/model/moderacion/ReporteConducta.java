package com.example.tpo.model.moderacion;

import java.time.Instant;

import com.example.tpo.model.dominio.Scrim;
import com.example.tpo.model.dominio.Usuario;
import com.example.tpo.model.estado.moderacion.IEstado;
import com.example.tpo.model.tipo.ITipoPenalidad;

public class ReporteConducta {
    private int idReporte;
    private Usuario reportante;
    private Usuario denunciado;
    private ITipoPenalidad motivo;
    private String descripcion;
    private Instant fecha;
    private IEstado estado;
    private Scrim scrim;
    private String sancion;

    public void resolver() {}

    public int getIdReporte() { return idReporte; }
    public void setIdReporte(int idReporte) { this.idReporte = idReporte; }
    public Usuario getReportante() { return reportante; }
    public void setReportante(Usuario reportante) { this.reportante = reportante; }
    public Usuario getDenunciado() { return denunciado; }
    public void setDenunciado(Usuario denunciado) { this.denunciado = denunciado; }
    public ITipoPenalidad getMotivo() { return motivo; }
    public void setMotivo(ITipoPenalidad motivo) { this.motivo = motivo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Instant getFecha() { return fecha; }
    public void setFecha(Instant fecha) { this.fecha = fecha; }
    public IEstado getEstado() { return estado; }
    public void setEstado(IEstado estado) { this.estado = estado; }
    public Scrim getScrim() { return scrim; }
    public void setScrim(Scrim scrim) { this.scrim = scrim; }
    public String getSancion() { return sancion; }
    public void setSancion(String sancion) { this.sancion = sancion; }
}
