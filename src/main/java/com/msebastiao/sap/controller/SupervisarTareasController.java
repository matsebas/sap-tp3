package com.msebastiao.sap.controller;

import com.msebastiao.sap.dao.ActividadDAO;
import com.msebastiao.sap.model.Actividad;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import java.sql.SQLException;
import java.util.List;

import static com.msebastiao.sap.view.AlertUtil.alert;

public class SupervisarTareasController {

    @FXML
    private ListView<String> tareasEnCursoList;

    @FXML
    private ListView<String> tareasFinalizadasList;

    @FXML
    private ListView<String> tareasCanceladasList;

    private ActividadDAO actividadDAO;


    @FXML
    private void initialize() {
        try {
            actividadDAO = new ActividadDAO();
            cargarTareas();
        } catch (SQLException e) {
            alert(Alert.AlertType.ERROR, "Error de base de datos", "Ocurrió un error inesperado.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleActualizarLista() {
        cargarTareas();
        alert(Alert.AlertType.INFORMATION, "Listas Actualizadas", "Las listas de tareas han sido actualizadas.");
    }

    private void cargarTareas() {
        try {
            tareasEnCursoList.getItems().clear();
            tareasFinalizadasList.getItems().clear();
            tareasCanceladasList.getItems().clear();

            List<Actividad> actividades = actividadDAO.getAll();

            tareasFinalizadasList.getItems().addAll(actividades.stream()
                    .filter(actividad -> "Finalizada".equals(actividad.getEstado()))
                    .map(Actividad::toString).toList());

            tareasCanceladasList.getItems().addAll(actividades.stream()
                    .filter(actividad -> "Cancelada".equals(actividad.getEstado()))
                    .map(Actividad::toString).toList());

            tareasEnCursoList.getItems().addAll(actividades.stream()
                    .filter(actividad -> "En curso".equals(actividad.getEstado()))
                    .map(Actividad::toString).toList());

        } catch (SQLException e) {
            alert(Alert.AlertType.ERROR, "Error de base de datos", "Ocurrió un error al cargar las listas de tareas.");
            e.printStackTrace();
        }
    }
}