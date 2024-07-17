package com.msebastiao.sap.dao;

import com.msebastiao.sap.model.Turno;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TurnoDAO implements DAO<Turno> {

    private Connection connection;

    public TurnoDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Turno turno) throws Exception {
        String query = "INSERT INTO Turnos (fecha, horaInicio, horaFin, estado, titularVehiculo, vehiculo, agenda_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, Date.valueOf(turno.getFecha()));
            stmt.setTime(2, Time.valueOf(turno.getHoraInicio()));
            stmt.setTime(3, Time.valueOf(turno.getHoraFin()));
            stmt.setString(4, turno.getEstado());
            stmt.setString(5, turno.getTitularVehiculo() != null ? turno.getTitularVehiculo().getDni() : null);
            stmt.setString(6, turno.getVehiculo() != null ? turno.getVehiculo().getModelo() : null);
            stmt.setInt(7, turno.getAgendaId());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    turno.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public Turno getById(int id) throws Exception {
        String query = "SELECT * FROM Turnos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int agendaId = rs.getInt("agenda_id");
                    LocalDate fecha = rs.getDate("fecha").toLocalDate();
                    LocalTime horaInicio = rs.getTime("horaInicio").toLocalTime();
                    LocalTime horaFin = rs.getTime("horaFin").toLocalTime();
                    String estado = rs.getString("estado");
                    // Aquí deberías obtener el TitularVehiculo y el Vehiculo según corresponda
                    // Este es un ejemplo básico
                    return new Turno(id, fecha, horaInicio, horaFin, estado, null, null, agendaId);
                }
            }
        }
        return null;
    }

    @Override
    public List<Turno> getAll() throws Exception {
        List<Turno> turnos = new ArrayList<>();
        String query = "SELECT * FROM Turnos";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int agendaId = rs.getInt("agenda_id");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                LocalTime horaInicio = rs.getTime("horaInicio").toLocalTime();
                LocalTime horaFin = rs.getTime("horaFin").toLocalTime();
                String estado = rs.getString("estado");
                // Aquí deberías obtener el TitularVehiculo y el Vehiculo según corresponda
                // Este es un ejemplo básico
                turnos.add(new Turno(id, fecha, horaInicio, horaFin, estado, null, null, agendaId));
            }
        }
        return turnos;
    }

    @Override
    public void update(Turno turno) throws Exception {
        String query = "UPDATE Turnos SET fecha = ?, horaInicio = ?, horaFin = ?, estado = ?, titularVehiculo = ?, vehiculo = ?, agenda_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(turno.getFecha()));
            stmt.setTime(2, Time.valueOf(turno.getHoraInicio()));
            stmt.setTime(3, Time.valueOf(turno.getHoraFin()));
            stmt.setString(4, turno.getEstado());
            stmt.setString(5, turno.getTitularVehiculo() != null ? turno.getTitularVehiculo().getDni() : null);
            stmt.setString(6, turno.getVehiculo() != null ? turno.getVehiculo().getModelo() : null);
            stmt.setInt(7, turno.getAgendaId());
            stmt.setInt(8, turno.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        String query = "DELETE FROM Turnos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Turno> getByAgendaId(int agendaId) throws Exception {
        List<Turno> turnos = new ArrayList<>();
        String query = "SELECT * FROM Turnos WHERE agenda_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, agendaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    LocalDate fecha = rs.getDate("fecha").toLocalDate();
                    LocalTime horaInicio = rs.getTime("horaInicio").toLocalTime();
                    LocalTime horaFin = rs.getTime("horaFin").toLocalTime();
                    String estado = rs.getString("estado");
                    // Aquí deberías obtener el TitularVehiculo y el Vehiculo según corresponda
                    // Este es un ejemplo básico
                    turnos.add(new Turno(id, fecha, horaInicio, horaFin, estado, null, null, agendaId));
                }
            }
        }
        return turnos;
    }
}
