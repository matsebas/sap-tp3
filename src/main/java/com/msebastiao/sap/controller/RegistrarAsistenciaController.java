package com.msebastiao.sap.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class RegistrarAsistenciaController {

    @FXML
    private TextField searchField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField dateField;

    @FXML
    private void handleSearch() {
        // Aquí deberías implementar la lógica para buscar el turno en la base de datos
        nameField.setText("Matias Sebastiao");  // Simulación de respuesta
        dateField.setText("06/03/2025 10:00 AM"); // Simulación de respuesta
    }

    @FXML
    private void handleConfirm() {
        // Aquí deberías implementar la lógica para confirmar la asistencia en la base de datos
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText("Asistencia confirmada!");
        alert.showAndWait();
    }
}