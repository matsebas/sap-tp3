package com.msebastiao.sap.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RealizarMantenimientoController {

    @FXML
    private TextField fichaField;

    @FXML
    private TextField clienteField;

    @FXML
    private TextField vehiculoField;

    @FXML
    private TextArea actividadesArea;

    @FXML
    private TextArea repuestosArea;

    @FXML
    private void handleFinalizarMantenimiento() {
        // Lógica para guardar los datos del mantenimiento
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mantenimiento Registrado");
        alert.setHeaderText(null);
        alert.setContentText("El mantenimiento ha sido registrado exitosamente.");
        alert.showAndWait();
    }
}
