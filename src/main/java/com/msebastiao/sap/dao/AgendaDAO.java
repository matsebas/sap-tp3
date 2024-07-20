package com.msebastiao.sap.dao;

import com.msebastiao.sap.database.DatabaseConnection;
import com.msebastiao.sap.model.Agenda;
import com.msebastiao.sap.model.Turno;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AgendaDAO implements DAO<Agenda> {

    private final Connection connection;

    public AgendaDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void insert(Agenda agenda) throws Exception {
        String query = "INSERT INTO agendas (fecha) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, Date.valueOf(agenda.getFecha()));
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int agendaId = generatedKeys.getInt(1);
                    agenda.setId(agendaId);
                    TurnoDAO turnoDAO = new TurnoDAO();
                    for (Turno turno : agenda.getTurnos()) {
                        turno.setAgendaId(agendaId);
                        turnoDAO.insert(turno);
                    }
                }
            }
        }
    }

    @Override
    public Agenda getById(int id) throws Exception {
        String query = "SELECT * FROM agendas WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    LocalDate fecha = rs.getDate("fecha").toLocalDate();
                    TurnoDAO turnoDAO = new TurnoDAO();
                    List<Turno> turnos = turnoDAO.getByAgendaId(id);
                    return new Agenda(id, fecha, turnos);
                }
            }
        }
        return null;
    }

    @Override
    public List<Agenda> getAll() throws Exception {
        List<Agenda> agendas = new ArrayList<>();
        String query = "SELECT * FROM agendas";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                TurnoDAO turnoDAO = new TurnoDAO();
                List<Turno> turnos = turnoDAO.getByAgendaId(id);
                agendas.add(new Agenda(id, fecha, turnos));
            }
        }
        return agendas;
    }

    @Override
    public void update(Agenda agenda) throws Exception {
        String query = "UPDATE agendas SET fecha = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(agenda.getFecha()));
            stmt.setInt(2, agenda.getId());
            stmt.executeUpdate();

            TurnoDAO turnoDAO = new TurnoDAO();
            for (Turno turno : agenda.getTurnos()) {
                if (turno.getId() == 0) {
                    turno.setAgendaId(agenda.getId());
                    turnoDAO.insert(turno);
                } else {
                    turnoDAO.update(turno);
                }
            }
        }
    }

    @Override
    public void delete(int id) throws Exception {
        TurnoDAO turnoDAO = new TurnoDAO();
        List<Turno> turnos = turnoDAO.getByAgendaId(id);
        for (Turno turno : turnos) {
            turnoDAO.delete(turno.getId());
        }

        String query = "DELETE FROM agendas WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
