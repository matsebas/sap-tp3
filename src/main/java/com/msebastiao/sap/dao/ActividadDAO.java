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
    public void insert(Actividad actividad) throws SQLException {
        String query = "INSERT INTO actividades (descripcion, tiempo_empleado, estado, ficha_mecanica_id) VALUES (?, ?, ?, ?)";
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
    public Actividad getById(int id) throws SQLException {
        String query = "SELECT * FROM actividades WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String descripcion = rs.getString("descripcion");
                    int tiempoEmpleado = rs.getInt("tiempo_empleado");
                    String estado = rs.getString("estado");
                    int fichaMecanicaId = rs.getInt("ficha_mecanica_id");
                    return new Actividad(id, descripcion, tiempoEmpleado, estado, fichaMecanicaId);
                }
            }
        }
        return null;
    }

    @Override
    public List<Actividad> getAll() throws SQLException {
        List<Actividad> actividades = new ArrayList<>();
        String query = "SELECT * FROM actividades";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String descripcion = rs.getString("descripcion");
                int tiempoEmpleado = rs.getInt("tiempo_empleado");
                String estado = rs.getString("estado");
                int fichaMecanicaId = rs.getInt("ficha_mecanica_id");
                actividades.add(new Actividad(id, descripcion, tiempoEmpleado, estado, fichaMecanicaId));
            }
        }
        return actividades;
    }

    @Override
    public void update(Actividad actividad) throws SQLException {
        String query = "UPDATE actividades SET descripcion = ?, tiempo_empleado = ?, estado = ?, ficha_mecanica_id = ? WHERE id = ?";
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
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM actividades WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Actividad> getByFichaMecanicaId(int fichaMecanicaId) throws SQLException {
        List<Actividad> actividades = new ArrayList<>();
        String query = "SELECT * FROM actividades WHERE ficha_mecanica_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, fichaMecanicaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String descripcion = rs.getString("descripcion");
                    int tiempoEmpleado = rs.getInt("tiempo_empleado");
                    String estado = rs.getString("estado");
                    actividades.add(new Actividad(id, descripcion, tiempoEmpleado, estado, fichaMecanicaId));
                }
            }
        }
        return actividades;
    }
}