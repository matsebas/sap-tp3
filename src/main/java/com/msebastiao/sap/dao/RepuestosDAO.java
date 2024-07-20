package com.msebastiao.sap.dao;

import com.msebastiao.sap.database.DatabaseConnection;
import com.msebastiao.sap.model.Repuestos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepuestosDAO implements DAO<Repuestos> {

    private final Connection connection;

    public RepuestosDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void insert(Repuestos repuesto) throws Exception {
        String query = "INSERT INTO repuestos (nombre, cantidad, ficha_mecanica_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, repuesto.getNombre());
            stmt.setInt(2, repuesto.getCantidad());
            stmt.setInt(3, repuesto.getFichaMecanicaId());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    repuesto.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public Repuestos getById(int id) throws Exception {
        String query = "SELECT * FROM repuestos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    int cantidad = rs.getInt("cantidad");
                    int fichaMecanicaId = rs.getInt("ficha_mecanica_id");
                    return new Repuestos(id, nombre, cantidad, fichaMecanicaId);
                }
            }
        }
        return null;
    }

    @Override
    public List<Repuestos> getAll() throws Exception {
        List<Repuestos> repuestos = new ArrayList<>();
        String query = "SELECT * FROM repuestos";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad");
                int fichaMecanicaId = rs.getInt("ficha_mecanica_id");
                repuestos.add(new Repuestos(id, nombre, cantidad, fichaMecanicaId));
            }
        }
        return repuestos;
    }

    @Override
    public void update(Repuestos repuesto) throws Exception {
        String query = "UPDATE repuestos SET nombre = ?, cantidad = ?, ficha_mecanica_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, repuesto.getNombre());
            stmt.setInt(2, repuesto.getCantidad());
            stmt.setInt(3, repuesto.getFichaMecanicaId());
            stmt.setInt(4, repuesto.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        String query = "DELETE FROM repuestos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Repuestos> getByFichaMecanicaId(int fichaMecanicaId) throws Exception {
        List<Repuestos> repuestos = new ArrayList<>();
        String query = "SELECT * FROM repuestos WHERE ficha_mecanica_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, fichaMecanicaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    int cantidad = rs.getInt("cantidad");
                    repuestos.add(new Repuestos(id, nombre, cantidad, fichaMecanicaId));
                }
            }
        }
        return repuestos;
    }
}
