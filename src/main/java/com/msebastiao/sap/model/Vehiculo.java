package com.msebastiao.sap.model;

public class Vehiculo {
    private int id;
    private String marca;
    private String modelo;
    private int anio;
    private TitularVehiculo titularVehiculo;

    public Vehiculo(int id, String marca, String modelo, int anio, TitularVehiculo titularVehiculo) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.titularVehiculo = titularVehiculo;
    }

    public Vehiculo(String marca, String modelo, int anio, TitularVehiculo titularVehiculo) {
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.titularVehiculo = titularVehiculo;
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

    public TitularVehiculo getTitularVehiculo() {
        return titularVehiculo;
    }

    public void setTitularVehiculo(TitularVehiculo titularVehiculo) {
        this.titularVehiculo = titularVehiculo;
    }
}
