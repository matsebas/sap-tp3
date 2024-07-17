package com.msebastiao.sap.controller;

import com.msebastiao.sap.dao.AgendaDAO;
import com.msebastiao.sap.dao.TitularVehiculoDAO;
import com.msebastiao.sap.dao.TurnoDAO;
import com.msebastiao.sap.database.DatabaseConnection;
import com.msebastiao.sap.model.Agenda;
import com.msebastiao.sap.model.TitularVehiculo;
import com.msebastiao.sap.model.Turno;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TurnoController {

    public List<Turno> obtenerTurnosDisponibles() throws SQLException {

        AgendaDAO agendaDAO = new AgendaDAO();
        List<Agenda> agendas = null;
        try {
            agendas = agendaDAO.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (agendas.isEmpty()) {
            return Collections.emptyList();
        }

        // Ordenar las agendas por fecha en orden descendente y tomar la más reciente
        agendas.sort(Comparator.comparing(Agenda::getFecha).reversed());
        Agenda agendaMasReciente = agendas.get(0);

        return agendaMasReciente.getTurnos();

    }

    public void solicitarTurno(int turnoId, String dni) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            TurnoDAO turnoDAO = new TurnoDAO(connection);
            TitularVehiculoDAO titularVehiculoDAO = new TitularVehiculoDAO(connection);

            Turno turno = turnoDAO.getById(turnoId);
            TitularVehiculo titular = titularVehiculoDAO.getByDni(dni);

            if (turno != null && titular != null) {
                turno.setEstado("Solicitado");
                turno.setTitularVehiculo(titular);
                turnoDAO.update(turno);
            } else {
                throw new Exception("Turno o Titular no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Turno obtenerTurnoPorId(int id) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            TurnoDAO turnoDAO = new TurnoDAO(connection);
            return turnoDAO.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void actualizarTurno(Turno turno) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            TurnoDAO turnoDAO = new TurnoDAO(connection);
            turnoDAO.update(turno);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarTurno(int id) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            TurnoDAO turnoDAO = new TurnoDAO(connection);
            turnoDAO.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}