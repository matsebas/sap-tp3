package com.msebastiao.sap.dao;

import com.msebastiao.sap.database.DatabaseConnection;
import com.msebastiao.sap.model.TitularVehiculo;
import com.msebastiao.sap.model.Vehiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TitularVehiculoDAO implements DAO<TitularVehiculo> {

    private final Connection connection;

    public TitularVehiculoDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void insert(TitularVehiculo titularVehiculo) throws SQLException {
        String query = "INSERT INTO titular_vehiculo (nombre, apellido, dni) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, titularVehiculo.getNombre());
            stmt.setString(2, titularVehiculo.getApellido());
            stmt.setString(3, titularVehiculo.getDni());
            stmt.executeUpdate();
        }
    }

    @Override
    public TitularVehiculo getById(int id) throws SQLException {
        String query = "SELECT * FROM titular_vehiculo WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String dni = rs.getString("dni");
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    TitularVehiculo titularVehiculo = new TitularVehiculo(id, dni, nombre, apellido);
                    titularVehiculo.setVehiculos(getVehiculosByTitularId(titularVehiculo));
                    return titularVehiculo;
                }
            }
        }
        return null;
    }

    @Override
    public List<TitularVehiculo> getAll() throws SQLException {
        List<TitularVehiculo> titulares = new ArrayList<>();
        String query = "SELECT * FROM titular_vehiculo";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                TitularVehiculo titularVehiculo = new TitularVehiculo(id, dni, nombre, apellido);
                titularVehiculo.setVehiculos(getVehiculosByTitularId(titularVehiculo));
                titulares.add(titularVehiculo);

            }
        }
        return titulares;
    }

    @Override
    public void update(TitularVehiculo titularVehiculo) throws SQLException {
        String query = "UPDATE titular_vehiculo SET dni = ?, nombre = ?, apellido = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, titularVehiculo.getDni());
            stmt.setString(2, titularVehiculo.getNombre());
            stmt.setString(3, titularVehiculo.getApellido());
            stmt.setInt(4, titularVehiculo.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM titular_vehiculo WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public TitularVehiculo getByDni(String dni) throws SQLException {
        String query = "SELECT * FROM titular_vehiculo WHERE dni = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, dni);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    TitularVehiculo titularVehiculo = new TitularVehiculo(id, dni, nombre, apellido);
                    titularVehiculo.setVehiculos(getVehiculosByTitularId(titularVehiculo));
                    return titularVehiculo;
                }
            }
        }
        return null;
    }

    private List<Vehiculo> getVehiculosByTitularId(TitularVehiculo titularVehiculo) throws SQLException {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String query = "SELECT * FROM vehiculos WHERE titular_vehiculo_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, titularVehiculo.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String marca = rs.getString("marca");
                    String modelo = rs.getString("modelo");
                    int anio = rs.getInt("anio");
                    vehiculos.add(new Vehiculo(id, marca, modelo, anio, titularVehiculo));
                }
            }
        }
        return vehiculos;
    }
}
