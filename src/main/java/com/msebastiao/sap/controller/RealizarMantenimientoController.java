package com.msebastiao.sap.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import static com.msebastiao.sap.view.AlertUtil.alert;

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
        alert(Alert.AlertType.INFORMATION, "Mantenimiento Finalizado", "El mantenimiento ha sido finalizado exitosamente.");
    }
}
