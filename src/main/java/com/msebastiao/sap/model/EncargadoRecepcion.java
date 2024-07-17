package com.msebastiao.sap.model;

public class EncargadoRecepcion {
    private String nombre;
    private String apellido;

    public EncargadoRecepcion(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public void consultarAgenda() {
        // Implementar lógica de consulta de agenda
    }

    public void registrarAsistencia() {
        // Implementar lógica de registro de asistencia
    }

    // Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
