package com.msebastiao.sap.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import static com.msebastiao.sap.view.AlertUtil.alert;

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
        alert(Alert.AlertType.INFORMATION, "Informes", "El informe para el mes de " + mesComboBox.getValue() + " no está disponible.");
    }
}