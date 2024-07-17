package com.msebastiao.sap.dao;

import com.msebastiao.sap.model.TitularVehiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TitularVehiculoDAO implements DAO<TitularVehiculo> {

    private Connection connection;

    public TitularVehiculoDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(TitularVehiculo titularVehiculo) throws Exception {
        String query = "INSERT INTO Titulares (nombre, apellido, dni) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, titularVehiculo.getNombre());
            stmt.setString(2, titularVehiculo.getApellido());
            stmt.setString(3, titularVehiculo.getDni());
            stmt.executeUpdate();
        }
    }

    @Override
    public TitularVehiculo getById(int id) throws Exception {
        String query = "SELECT * FROM Titulares WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    String dni = rs.getString("dni");
                    // Aquí deberías obtener la lista de Vehiculos según corresponda
                    // Este es un ejemplo básico
                    return new TitularVehiculo(id, nombre, apellido, dni, new ArrayList<>());
                }
            }
        }
        return null;
    }

    public TitularVehiculo getByDni(String dni) throws Exception {
        String query = "SELECT * FROM Titulares WHERE dni = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, dni);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    // Aquí deberías obtener la lista de Vehiculos según corresponda
                    // Este es un ejemplo básico
                    return new TitularVehiculo(id, nombre, apellido, dni, new ArrayList<>());
                }
            }
        }
        return null;
    }

    @Override
    public List<TitularVehiculo> getAll() throws Exception {
        List<TitularVehiculo> titulares = new ArrayList<>();
        String query = "SELECT * FROM Titulares";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String dni = rs.getString("dni");
                // Aquí deberías obtener la lista de Vehiculos según corresponda
                // Este es un ejemplo básico
                titulares.add(new TitularVehiculo(id, nombre, apellido, dni, new ArrayList<>()));
            }
        }
        return titulares;
    }

    @Override
    public void update(TitularVehiculo titularVehiculo) throws Exception {
        String query = "UPDATE Titulares SET nombre = ?, apellido = ?, dni = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, titularVehiculo.getNombre());
            stmt.setString(2, titularVehiculo.getApellido());
            stmt.setString(3, titularVehiculo.getDni());
            stmt.setInt(4, titularVehiculo.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        String query = "DELETE FROM Titulares WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
