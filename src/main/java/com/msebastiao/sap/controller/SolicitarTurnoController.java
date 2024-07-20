package com.msebastiao.sap.controller;

import com.msebastiao.sap.dao.TitularVehiculoDAO;
import com.msebastiao.sap.dao.TurnoDAO;
import com.msebastiao.sap.model.TitularVehiculo;
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
import java.util.Collections;
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

    @FXML
    public void initialize() {
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        horaInicioColumn.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));
        horaFinColumn.setCellValueFactory(new PropertyValueFactory<>("horaFin"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));

        cargarTurnos();
    }

    private void cargarTurnos() {

        List<Turno> turnos = Collections.emptyList();
        try {
            TurnoDAO turnoDAO = new TurnoDAO();
            turnos = turnoDAO.getAll();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Turnos");
            alert.setHeaderText(null);
            alert.setContentText("No se encontraron turnos disponibles");
            alert.showAndWait();
        }
        turnosTable.getItems().setAll(turnos);
    }


    @FXML
    private void handleSolicitarTurno() {
        try {
            Turno selectedTurno = turnosTable.getSelectionModel().getSelectedItem();
            if (selectedTurno == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, seleccione un turno disponible.");
                alert.showAndWait();
                return;
            }
            String dni = dniField.getText();
            TitularVehiculoDAO titularVehiculoDAO = new TitularVehiculoDAO();
            TitularVehiculo titular = titularVehiculoDAO.getByDni(dni);

            if (titular == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("El Titular no existe o no fue encontrado con el DNI ingresado");
                alert.showAndWait();
                return;
            }

            selectedTurno.setEstado("Solicitado");
            selectedTurno.setTitularVehiculo(titular);
            TurnoDAO turnoDAO = new TurnoDAO();
            turnoDAO.update(selectedTurno);

            cargarTurnos();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Turno Solicitado");
            alert.setHeaderText(null);
            alert.setContentText("El turno ha sido solicitado exitosamente.");
            alert.showAndWait();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Turno");
            alert.setHeaderText(null);
            alert.setContentText("El turno ha sido solicitado exitosamente.");
            alert.showAndWait();
        }
    }
}
