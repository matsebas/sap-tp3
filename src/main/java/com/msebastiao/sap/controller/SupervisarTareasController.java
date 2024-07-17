package com.msebastiao.sap.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

public class SupervisarTareasController {

    @FXML
    private ListView<String> tareasList;

    @FXML
    private void initialize() {
        tareasList.getItems().addAll(
                "Cambio de aceite - Juan Pérez",
                "Revisión de frenos - Ana López",
                "Alineación - Mario Casas"
        );
    }

    @FXML
    private void handleActualizarLista() {
        // Lógica para actualizar la lista de tareas
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Lista Actualizada");
        alert.setHeaderText(null);
        alert.setContentText("La lista de tareas ha sido actualizada.");
        alert.showAndWait();
    }

    @FXML
    private void handleGenerarReporte() {
        // Lógica para generar el reporte
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reporte Generado");
        alert.setHeaderText(null);
        alert.setContentText("El reporte ha sido generado exitosamente.");
        alert.showAndWait();
    }
}