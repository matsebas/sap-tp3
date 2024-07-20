package com.msebastiao.sap.controller;

import com.msebastiao.sap.dao.AgendaDAO;
import com.msebastiao.sap.dao.TitularVehiculoDAO;
import com.msebastiao.sap.dao.TurnoDAO;
import com.msebastiao.sap.model.Agenda;
import com.msebastiao.sap.model.TitularVehiculo;
import com.msebastiao.sap.model.Turno;
import javafx.scene.control.Alert;

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
        try {
            TurnoDAO turnoDAO = new TurnoDAO();
            TitularVehiculoDAO titularVehiculoDAO = new TitularVehiculoDAO();

            Turno turno = turnoDAO.getById(turnoId);
            TitularVehiculo titular = titularVehiculoDAO.getByDni(dni);

            if (turno != null && titular != null) {
                turno.setEstado("Solicitado");
                turno.setTitularVehiculo(titular);
                turnoDAO.update(turno);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Turnos");
                alert.setHeaderText(null);
                alert.setContentText("El Turno fue solicitado con éxito!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Turnos");
                alert.setHeaderText(null);
                alert.setContentText("El Titular no existe o no fue encontrado con el DNI ingresado");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}