package com.msebastiao.sap.dao;

import com.msebastiao.sap.model.Vehiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO implements DAO<Vehiculo> {

    private Connection connection;

    public VehiculoDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Vehiculo vehiculo) throws Exception {
        String query = "INSERT INTO Vehiculos (marca, modelo, anio, titular_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, vehiculo.getMarca());
            stmt.setString(2, vehiculo.getModelo());
            stmt.setInt(3, vehiculo.getAnio());
            stmt.setInt(4, vehiculo.getTitularId());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    vehiculo.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public Vehiculo getById(int id) throws Exception {
        String query = "SELECT * FROM Vehiculos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String marca = rs.getString("marca");
                    String modelo = rs.getString("modelo");
                    int anio = rs.getInt("anio");
                    int titularId = rs.getInt("titular_id");
                    return new Vehiculo(id, marca, modelo, anio, titularId);
                }
            }
        }
        return null;
    }

    @Override
    public List<Vehiculo> getAll() throws Exception {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String query = "SELECT * FROM Vehiculos";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                int anio = rs.getInt("anio");
                int titularId = rs.getInt("titular_id");
                vehiculos.add(new Vehiculo(id, marca, modelo, anio, titularId));
            }
        }
        return vehiculos;
    }

    @Override
    public void update(Vehiculo vehiculo) throws Exception {
        String query = "UPDATE Vehiculos SET marca = ?, modelo = ?, anio = ?, titular_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, vehiculo.getMarca());
            stmt.setString(2, vehiculo.getModelo());
            stmt.setInt(3, vehiculo.getAnio());
            stmt.setInt(4, vehiculo.getTitularId());
            stmt.setInt(5, vehiculo.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        String query = "DELETE FROM Vehiculos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Vehiculo> getByTitularId(int titularId) throws Exception {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String query = "SELECT * FROM Vehiculos WHERE titular_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, titularId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String marca = rs.getString("marca");
                    String modelo = rs.getString("modelo");
                    int anio = rs.getInt("anio");
                    vehiculos.add(new Vehiculo(id, marca, modelo, anio, titularId));
                }
            }
        }
        return vehiculos;
    }
}
