package com.msebastiao.sap.dao;

import com.msebastiao.sap.model.Informe;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InformeDAO implements DAO<Informe> {

    private Connection connection;

    public InformeDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Informe informe) throws Exception {
        String query = "INSERT INTO Informes (fecha, contenido) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(informe.getFecha()));
            stmt.setString(2, informe.getContenido());
            stmt.executeUpdate();
        }
    }

    @Override
    public Informe getById(int id) throws Exception {
        String query = "SELECT * FROM Informes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    LocalDate fecha = rs.getDate("fecha").toLocalDate();
                    String contenido = rs.getString("contenido");
                    return new Informe(fecha, contenido);
                }
            }
        }
        return null;
    }

    @Override
    public List<Informe> getAll() throws Exception {
        List<Informe> informes = new ArrayList<>();
        String query = "SELECT * FROM Informes";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                String contenido = rs.getString("contenido");
                informes.add(new Informe(fecha, contenido));
            }
        }
        return informes;
    }

    @Override
    public void update(Informe informe) throws Exception {
        String query = "UPDATE Informes SET fecha = ?, contenido = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(informe.getFecha()));
            stmt.setString(2, informe.getContenido());
            stmt.setInt(3, informe.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        String query = "DELETE FROM Informes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
