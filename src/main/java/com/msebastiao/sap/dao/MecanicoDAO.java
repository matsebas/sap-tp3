package com.msebastiao.sap.dao;

import com.msebastiao.sap.model.Mecanico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MecanicoDAO implements DAO<Mecanico> {

    private Connection connection;

    public MecanicoDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Mecanico mecanico) throws Exception {
        String query = "INSERT INTO Mecanicos (nombre, apellido) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, mecanico.getNombre());
            stmt.setString(2, mecanico.getApellido());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    mecanico.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public Mecanico getById(int id) throws Exception {
        String query = "SELECT * FROM Mecanicos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    return new Mecanico(id, nombre, apellido);
                }
            }
        }
        return null;
    }

    @Override
    public List<Mecanico> getAll() throws Exception {
        List<Mecanico> mecanicos = new ArrayList<>();
        String query = "SELECT * FROM Mecanicos";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                mecanicos.add(new Mecanico(id, nombre, apellido));
            }
        }
        return mecanicos;
    }

    @Override
    public void update(Mecanico mecanico) throws Exception {
        String query = "UPDATE Mecanicos SET nombre = ?, apellido = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, mecanico.getNombre());
            stmt.setString(2, mecanico.getApellido());
            stmt.setInt(3, mecanico.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        String query = "DELETE FROM Mecanicos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}