package com.example.tpo.model.moderacion;

public class BotModerador implements IManejadorReporte {
    private IManejadorReporte siguiente;

    @Override
    public void manejar(ReporteConducta reporte) { if (siguiente != null) siguiente.manejar(reporte); }

    @Override
    public void setSiguiente(IManejadorReporte m) { this.siguiente = m; }
}

