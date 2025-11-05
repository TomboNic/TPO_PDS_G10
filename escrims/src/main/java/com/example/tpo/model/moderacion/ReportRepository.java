package com.example.tpo.model.moderacion;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Repositorio en memoria para reportes de conducta.
 * No persistente: reiniciar la app limpia la lista.
 */
public final class ReportRepository {
    private static ReportRepository instancia;
    private final List<ReporteConducta> reportes = new ArrayList<>();
    private int seq = 1;

    private ReportRepository() {}

    public static synchronized ReportRepository get() {
        if (instancia == null) instancia = new ReportRepository();
        return instancia;
    }

    public synchronized ReporteConducta add(ReporteConducta r) {
        if (r == null) return null;
        r.setIdReporte(seq++);
        if (r.getFecha() == null) r.setFecha(Instant.now());
        reportes.add(r);
        return r;
    }

    public synchronized List<ReporteConducta> list() {
        return Collections.unmodifiableList(new ArrayList<>(reportes));
    }

    public synchronized ReporteConducta find(int id) {
        return reportes.stream().filter(x -> x.getIdReporte() == id).findFirst().orElse(null);
    }
}

