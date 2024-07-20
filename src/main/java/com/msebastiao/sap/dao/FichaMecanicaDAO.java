package com.msebastiao.sap.dao;

import com.msebastiao.sap.database.DatabaseConnection;
import com.msebastiao.sap.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FichaMecanicaDAO implements DAO<FichaMecanica> {

    private final Connection connection;

    public FichaMecanicaDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void insert(FichaMecanica ficha) throws Exception {
        String query = "INSERT INTO fichas_mecanicas (mecanico_id, titular_vehiculo_id, vehiculo_id, fecha_inicio, fecha_fin, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, ficha.getMecanico().getId());
            stmt.setInt(2, ficha.getTitularVehiculo().getId());
            stmt.setInt(3, ficha.getVehiculo().getId());
            stmt.setDate(4, Date.valueOf(ficha.getFechaInicio()));
            stmt.setDate(5, Date.valueOf(ficha.getFechaFin()));
            stmt.setString(6, ficha.getEstado());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int fichaId = generatedKeys.getInt(1);
                    ficha.setId(fichaId);
                    ActividadDAO actividadDAO = new ActividadDAO();
                    for (Actividad actividad : ficha.getActividadesRealizadas()) {
                        actividadDAO.insert(actividad);
                    }
                    RepuestosDAO repuestosDAO = new RepuestosDAO();
                    for (Repuestos repuesto : ficha.getRepuestosEmpleados()) {
                        repuestosDAO.insert(repuesto);
                    }
                }
            }
        }
    }

    @Override
    public FichaMecanica getById(int id) throws Exception {
        String query = "SELECT * FROM fichas_mecanicas WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int mecanicoId = rs.getInt("mecanico_id");
                    int titularVehiculoId = rs.getInt("titularVehiculo_id");
                    int vehiculoId = rs.getInt("vehiculo_id");
                    LocalDate fechaInicio = rs.getDate("fecha_inicio").toLocalDate();
                    LocalDate fechaFin = rs.getDate("fecha_fin").toLocalDate();
                    String estado = rs.getString("estado");

                    MecanicoDAO mecanicoDAO = new MecanicoDAO();
                    Mecanico mecanico = mecanicoDAO.getById(mecanicoId);

                    TitularVehiculoDAO titularVehiculoDAO = new TitularVehiculoDAO();
                    TitularVehiculo titularVehiculo = titularVehiculoDAO.getById(titularVehiculoId);

                    VehiculoDAO vehiculoDAO = new VehiculoDAO();
                    Vehiculo vehiculo = vehiculoDAO.getById(vehiculoId);

                    ActividadDAO actividadDAO = new ActividadDAO();
                    List<Actividad> actividades = actividadDAO.getByFichaMecanicaId(id);

                    RepuestosDAO repuestosDAO = new RepuestosDAO();
                    List<Repuestos> repuestos = repuestosDAO.getByFichaMecanicaId(id);

                    return new FichaMecanica(id, mecanico, titularVehiculo, vehiculo, fechaInicio, fechaFin, estado, actividades, repuestos);
                }
            }
        }
        return null;
    }

    @Override
    public List<FichaMecanica> getAll() throws Exception {
        List<FichaMecanica> fichas = new ArrayList<>();
        String query = "SELECT * FROM fichas_mecanicas";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int mecanicoId = rs.getInt("mecanico_id");
                int titularVehiculoId = rs.getInt("titular_vehiculo_id");
                int vehiculoId = rs.getInt("vehiculo_id");
                LocalDate fechaInicio = rs.getDate("fecha_inicio").toLocalDate();
                LocalDate fechaFin = rs.getDate("fecha_fin").toLocalDate();
                String estado = rs.getString("estado");

                MecanicoDAO mecanicoDAO = new MecanicoDAO();
                Mecanico mecanico = mecanicoDAO.getById(mecanicoId);

                TitularVehiculoDAO titularVehiculoDAO = new TitularVehiculoDAO();
                TitularVehiculo titularVehiculo = titularVehiculoDAO.getById(titularVehiculoId);

                VehiculoDAO vehiculoDAO = new VehiculoDAO();
                Vehiculo vehiculo = vehiculoDAO.getById(vehiculoId);

                ActividadDAO actividadDAO = new ActividadDAO();
                List<Actividad> actividades = actividadDAO.getByFichaMecanicaId(id);

                RepuestosDAO repuestosDAO = new RepuestosDAO();
                List<Repuestos> repuestos = repuestosDAO.getByFichaMecanicaId(id);

                fichas.add(new FichaMecanica(id, mecanico, titularVehiculo, vehiculo, fechaInicio, fechaFin, estado, actividades, repuestos));
            }
        }
        return fichas;
    }

    @Override
    public void update(FichaMecanica ficha) throws Exception {
        String query = "UPDATE fichas_mecanicas SET mecanico_id = ?, titular_vehiculo_id = ?, vehiculo_id = ?, fecha_inicio = ?, fecha_fin = ?, estado = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, ficha.getMecanico().getId());
            stmt.setInt(2, ficha.getTitularVehiculo().getId());
            stmt.setInt(3, ficha.getVehiculo().getId());
            stmt.setDate(4, Date.valueOf(ficha.getFechaInicio()));
            stmt.setDate(5, Date.valueOf(ficha.getFechaFin()));
            stmt.setString(6, ficha.getEstado());
            stmt.setInt(7, ficha.getId());
            stmt.executeUpdate();

            ActividadDAO actividadDAO = new ActividadDAO();
            for (Actividad actividad : ficha.getActividadesRealizadas()) {
                if (actividad.getId() == 0) {
                    actividadDAO.insert(actividad);
                } else {
                    actividadDAO.update(actividad);
                }
            }

            RepuestosDAO repuestosDAO = new RepuestosDAO();
            for (Repuestos repuesto : ficha.getRepuestosEmpleados()) {
                if (repuesto.getId() == 0) {
                    repuestosDAO.insert(repuesto);
                } else {
                    repuestosDAO.update(repuesto);
                }
            }
        }
    }

    @Override
    public void delete(int id) throws Exception {
        ActividadDAO actividadDAO = new ActividadDAO();
        List<Actividad> actividades = actividadDAO.getByFichaMecanicaId(id);
        for (Actividad actividad : actividades) {
            actividadDAO.delete(actividad.getId());
        }

        RepuestosDAO repuestosDAO = new RepuestosDAO();
        List<Repuestos> repuestos = repuestosDAO.getByFichaMecanicaId(id);
        for (Repuestos repuesto : repuestos) {
            repuestosDAO.delete(repuesto.getId());
        }

        String query = "DELETE FROM fichas_mecanicas WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}