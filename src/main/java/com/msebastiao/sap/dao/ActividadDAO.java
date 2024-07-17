package com.msebastiao.sap.dao;

import com.msebastiao.sap.database.DatabaseConnection;
import com.msebastiao.sap.model.Actividad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActividadDAO implements DAO<Actividad> {

    private final Connection connection;

    public ActividadDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void insert(Actividad actividad) throws Exception {
        String query = "INSERT INTO Actividades (descripcion, tiempoEmpleado, estado, fichaMecanica_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, actividad.getDescripcion());
            stmt.setInt(2, actividad.getTiempoEmpleado());
            stmt.setString(3, actividad.getEstado());
            stmt.setInt(4, actividad.getFichaMecanicaId());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    actividad.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public Actividad getById(int id) throws Exception {
        String query = "SELECT * FROM Actividades WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String descripcion = rs.getString("descripcion");
                    int tiempoEmpleado = rs.getInt("tiempoEmpleado");
                    String estado = rs.getString("estado");
                    int fichaMecanicaId = rs.getInt("fichaMecanica_id");
                    return new Actividad(id, descripcion, tiempoEmpleado, estado, fichaMecanicaId);
                }
            }
        }
        return null;
    }

    @Override
    public List<Actividad> getAll() throws Exception {
        List<Actividad> actividades = new ArrayList<>();
        String query = "SELECT * FROM Actividades";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String descripcion = rs.getString("descripcion");
                int tiempoEmpleado = rs.getInt("tiempoEmpleado");
                String estado = rs.getString("estado");
                int fichaMecanicaId = rs.getInt("fichaMecanica_id");
                actividades.add(new Actividad(id, descripcion, tiempoEmpleado, estado, fichaMecanicaId));
            }
        }
        return actividades;
    }

    @Override
    public void update(Actividad actividad) throws Exception {
        String query = "UPDATE Actividades SET descripcion = ?, tiempoEmpleado = ?, estado = ?, fichaMecanica_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, actividad.getDescripcion());
            stmt.setInt(2, actividad.getTiempoEmpleado());
            stmt.setString(3, actividad.getEstado());
            stmt.setInt(4, actividad.getFichaMecanicaId());
            stmt.setInt(5, actividad.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        String query = "DELETE FROM Actividades WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Actividad> getByFichaMecanicaId(int fichaMecanicaId) throws Exception {
        List<Actividad> actividades = new ArrayList<>();
        String query = "SELECT * FROM Actividades WHERE fichaMecanica_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, fichaMecanicaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String descripcion = rs.getString("descripcion");
                    int tiempoEmpleado = rs.getInt("tiempoEmpleado");
                    String estado = rs.getString("estado");
                    actividades.add(new Actividad(id, descripcion, tiempoEmpleado, estado, fichaMecanicaId));
                }
            }
        }
        return actividades;
    }
}