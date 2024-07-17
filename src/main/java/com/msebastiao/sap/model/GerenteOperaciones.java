package com.msebastiao.sap.model;

public class GerenteOperaciones {
    private String nombre;
    private String apellido;

    public GerenteOperaciones(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public void controlarTrabajo() {
        // Implementar lógica de control de trabajo
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
