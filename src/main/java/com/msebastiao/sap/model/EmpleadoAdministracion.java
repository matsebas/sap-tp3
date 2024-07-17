package com.msebastiao.sap.model;

public class EmpleadoAdministracion {
    private String nombre;
    private String apellido;

    public EmpleadoAdministracion(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public void generarInformeMensual() {
        // Implementar lógica de generación de informe mensual
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
