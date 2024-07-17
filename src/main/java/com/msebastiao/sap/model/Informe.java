package com.msebastiao.sap.model;

import java.time.LocalDate;

public class Informe {
    private int id;
    private LocalDate fecha;
    private String contenido;

    public Informe(int id, LocalDate fecha, String contenido) {
        this.id = id;
        this.fecha = fecha;
        this.contenido = contenido;
    }

    public Informe(LocalDate fecha, String contenido) {
        this.fecha = fecha;
        this.contenido = contenido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
