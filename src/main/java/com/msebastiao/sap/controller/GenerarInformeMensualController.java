package com.msebastiao.sap.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

public class GenerarInformeMensualController {

    @FXML
    private ComboBox<String> mesComboBox;

    @FXML
    private ComboBox<String> tipoComboBox;

    @FXML
    private void initialize() {
        mesComboBox.setValue("Enero");
        tipoComboBox.setValue("Servicios realizados");
    }

    @FXML
    private void handleGenerarInforme() {
        // Lógica para generar el informe
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informe Generado");
        alert.setHeaderText(null);
        alert.setContentText("El informe para el mes de " + mesComboBox.getValue() + " ha sido generado exitosamente.");
        alert.showAndWait();
    }
}