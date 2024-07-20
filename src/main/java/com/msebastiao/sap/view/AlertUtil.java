package com.msebastiao.sap.view;

import javafx.scene.control.Alert;

/**
 * Clase para mostrar alertas en la interfaz de usuario.
 */
public class AlertUtil {

    /**
     * Muestra una alerta en la interfaz de usuario con el tipo de alerta, el título y el mensaje dado.
     *
     * @param alertType {@link Alert.AlertType} del tipo de alerta.
     * @param titulo    el título de la alerta.
     * @param mensaje   el mensaje de la alerta.
     */
    public static void alert(Alert.AlertType alertType, String titulo, String mensaje) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
