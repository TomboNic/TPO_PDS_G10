package com.example.tpo.model.moderacion;

public interface IManejadorReporte {
    void manejar(ReporteConducta reporte);
    void setSiguiente(IManejadorReporte m);
}

