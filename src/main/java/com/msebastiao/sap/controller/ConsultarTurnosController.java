package com.msebastiao.sap.controller;

import com.msebastiao.sap.dao.TurnoDAO;
import com.msebastiao.sap.model.Turno;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

import static com.msebastiao.sap.view.AlertUtil.alert;

public class ConsultarTurnosController {

    @FXML
    private TextField dniField;
    @FXML
    private TableView<Turno> tablaTurnos;
    @FXML
    private TableColumn<Turno, String> fechaColumn;
    @FXML
    private TableColumn<Turno, String> horaInicioColumn;
    @FXML
    private TableColumn<Turno, String> horaFinColumn;
    @FXML
    private TableColumn<Turno, String> estadoColumn;

    @FXML
    private void initialize() {
        // Configurar las columnas de la tabla
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        horaInicioColumn.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));
        horaFinColumn.setCellValueFactory(new PropertyValueFactory<>("horaFin"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
    }

    @FXML
    private void buscarTurnos() {
        String dni = dniField.getText();

        if (dni.isEmpty()) {
            alert(Alert.AlertType.ERROR, "Error", "Por favor, ingrese un DNI.");
            return;
        }

        try {

            TurnoDAO turnoDAO = new TurnoDAO();

            List<Turno> turnos = turnoDAO.getAll().stream().filter(turno -> turno.getTitularVehiculo() != null
                    && dni.equals(turno.getTitularVehiculo().getDni())).toList();

            if (turnos.isEmpty()) {
                alert(Alert.AlertType.WARNING, "No se encontraron turnos", "No se encontraron turnos para el DNI ingresado.");
            } else {
                tablaTurnos.setItems(FXCollections.observableArrayList(turnos));
            }
        } catch (Exception e) {
            alert(Alert.AlertType.ERROR, "Error de base de datos", "Ocurrió un error al consultar la base de datos.");
            e.printStackTrace();
        }
    }
}
