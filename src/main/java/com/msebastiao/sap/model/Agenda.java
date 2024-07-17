package com.msebastiao.sap.model;

import java.time.LocalDate;
import java.util.List;

public class Agenda {
    private int id;
    private LocalDate fecha;
    private List<Turno> turnos;

    public Agenda(int id, LocalDate fecha, List<Turno> turnos) {
        this.id = id;
        this.fecha = fecha;
        this.turnos = turnos;
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

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }

    public void agregarTurno(Turno turno) {
        turnos.add(turno);
    }

    public void eliminarTurno(Turno turno) {
        turnos.remove(turno);
    }
}
