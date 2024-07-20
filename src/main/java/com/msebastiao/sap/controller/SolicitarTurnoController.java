package com.msebastiao.sap.controller;

import com.msebastiao.sap.model.Turno;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SolicitarTurnoController {

    @FXML
    private TableView<Turno> turnosTable;
    @FXML
    private TableColumn<Turno, LocalDate> fechaColumn;
    @FXML
    private TableColumn<Turno, LocalTime> horaInicioColumn;
    @FXML
    private TableColumn<Turno, LocalTime> horaFinColumn;
    @FXML
    private TableColumn<Turno, String> estadoColumn;
    @FXML
    private TextField dniField;

    private TurnoController turnoController;

    @FXML
    public void initialize() {
        turnoController = new TurnoController();

        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        horaInicioColumn.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));
        horaFinColumn.setCellValueFactory(new PropertyValueFactory<>("horaFin"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));

        cargarTurnosDisponibles();
    }

    private void cargarTurnosDisponibles() {
        List<Turno> turnos;
        try {
            turnos = turnoController.obtenerTurnosDisponibles();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        turnosTable.getItems().setAll(turnos);
    }

    @FXML
    private void handleSolicitarTurno() {
        Turno selectedTurno = turnosTable.getSelectionModel().getSelectedItem();
        if (selectedTurno != null) {
            String dni = dniField.getText();
            turnoController.solicitarTurno(selectedTurno.getId(), dni);
            cargarTurnosDisponibles();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Turno Solicitado");
            alert.setHeaderText(null);
            alert.setContentText("El turno ha sido solicitado exitosamente.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione un turno disponible.");
            alert.showAndWait();
        }
    }
}
