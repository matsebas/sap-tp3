package com.msebastiao.sap.dao;

import com.msebastiao.sap.database.DatabaseConnection;
import com.msebastiao.sap.model.TitularVehiculo;
import com.msebastiao.sap.model.Vehiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO implements DAO<Vehiculo> {

    private Connection connection;

    public VehiculoDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void insert(Vehiculo vehiculo) throws Exception {
        String query = "INSERT INTO vehiculos (marca, modelo, anio, titular_vehiculo_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, vehiculo.getMarca());
            stmt.setString(2, vehiculo.getModelo());
            stmt.setInt(3, vehiculo.getAnio());
            if (vehiculo.getTitularVehiculo() == null) {
                stmt.setNull(4, Types.INTEGER);
            } else {
                stmt.setInt(4, vehiculo.getTitularVehiculo().getId());
            }
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
        String query = "SELECT * FROM vehiculos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String marca = rs.getString("marca");
                    String modelo = rs.getString("modelo");
                    int anio = rs.getInt("anio");
                    int titularVehiculoId = rs.getInt("titular_vehiculo_id");
                    TitularVehiculoDAO titularVehiculoDAO = new TitularVehiculoDAO();
                    TitularVehiculo titularVehiculo = titularVehiculoDAO.getById(titularVehiculoId);
                    return new Vehiculo(id, marca, modelo, anio, titularVehiculo);
                }
            }
        }
        return null;
    }

    @Override
    public List<Vehiculo> getAll() throws Exception {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String query = "SELECT * FROM vehiculos";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                int anio = rs.getInt("anio");
                int titularId = rs.getInt("titular_id");
                TitularVehiculoDAO titularVehiculoDAO = new TitularVehiculoDAO();
                TitularVehiculo titularVehiculo = titularVehiculoDAO.getById(titularId);
                vehiculos.add(new Vehiculo(id, marca, modelo, anio, titularVehiculo));
            }
        }
        return vehiculos;
    }

    @Override
    public void update(Vehiculo vehiculo) throws Exception {
        String query = "UPDATE vehiculos SET marca = ?, modelo = ?, anio = ?, titular_vehiculo_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, vehiculo.getMarca());
            stmt.setString(2, vehiculo.getModelo());
            stmt.setInt(3, vehiculo.getAnio());
            if (vehiculo.getTitularVehiculo() == null) {
                stmt.setNull(4, Types.INTEGER);
            } else {
                stmt.setInt(4, vehiculo.getTitularVehiculo().getId());
            }
            stmt.setInt(5, vehiculo.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        String query = "DELETE FROM vehiculos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
