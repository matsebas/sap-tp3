package com.msebastiao.sap.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import static com.msebastiao.sap.view.AlertUtil.alert;

public class SupervisarTareasController {

    @FXML
    private ListView<String> tareasList;

    @FXML
    private void initialize() {
        tareasList.getItems().addAll("Cambio de aceite - Juan Pérez", "Revisión de frenos - Ana López", "Alineación - Mario Casas");
    }

    @FXML
    private void handleActualizarLista() {
        // Lógica para actualizar la lista de tareas
        alert(Alert.AlertType.INFORMATION, "Lista Actualizada", "La lista de tareas ha sido actualizada.");
    }

    @FXML
    private void handleGenerarReporte() {
        // Lógica para generar el reporte
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert(Alert.AlertType.INFORMATION, "Reporte Generado", "El reporte ha sido generado exitosamente.");
    }
}