package com.msebastiao.sap.model;

public class Vehiculo {
    private int id;
    private String marca;
    private String modelo;
    private int anio;
    private int titularId; // Referencia al ID del titular del vehículo

    public Vehiculo(int id, String marca, String modelo, int anio, int titularId) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.titularId = titularId;
    }

    public Vehiculo(String marca, String modelo, int anio, int titularId) {
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.titularId = titularId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getTitularId() {
        return titularId;
    }

    public void setTitularId(int titularId) {
        this.titularId = titularId;
    }
}
