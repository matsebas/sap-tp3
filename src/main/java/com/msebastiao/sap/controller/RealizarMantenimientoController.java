package com.msebastiao.sap.controller;

import com.msebastiao.sap.dao.ActividadDAO;
import com.msebastiao.sap.dao.FichaMecanicaDAO;
import com.msebastiao.sap.dao.RepuestoDAO;
import com.msebastiao.sap.model.Actividad;
import com.msebastiao.sap.model.FichaMecanica;
import com.msebastiao.sap.model.Repuesto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

import static com.msebastiao.sap.view.AlertUtil.alert;

public class RealizarMantenimientoController {

    @FXML
    private ComboBox<FichaMecanica> fichasComboBox;
    @FXML
    private TextField marcaField, modeloField, anioField;
    @FXML
    private TextField descripcionActividadField, nombreRepuestoField;
    @FXML
    private ComboBox<String> estadoActividadComboBox;
    @FXML
    private Spinner<Integer> cantidadRepuestoSpinner;
    @FXML
    private TableView<Actividad> tablaActividades;
    @FXML
    private TableColumn<Actividad, String> descripcionActividadColumn, estadoActividadColumn;
    @FXML
    private TableView<Repuesto> tablaRepuestos;
    @FXML
    private TableColumn<Repuesto, String> nombreRepuestoColumn;
    @FXML
    private TableColumn<Repuesto, Integer> cantidadRepuestoColumn;
    @FXML
    private Button agregarActividadButton;
    @FXML
    private Button agregarRepuestoButton;

    private FichaMecanicaDAO fichaMecanicaDAO;
    private ActividadDAO actividadDAO;
    private RepuestoDAO repuestoDAO;
    private ObservableList<Actividad> actividadesData;
    private ObservableList<Repuesto> repuestosData;
    private FichaMecanica fichaActual;

    @FXML
    private void initialize() throws SQLException {
        fichaMecanicaDAO = new FichaMecanicaDAO();
        actividadDAO = new ActividadDAO();
        repuestoDAO = new RepuestoDAO();
        actividadesData = FXCollections.observableArrayList();
        repuestosData = FXCollections.observableArrayList();
        tablaActividades.setItems(actividadesData);
        tablaRepuestos.setItems(repuestosData);

        descripcionActividadColumn.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        estadoActividadColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
        nombreRepuestoColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        cantidadRepuestoColumn.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 0); // Rango de 0 a 100, valor inicial 0
        cantidadRepuestoSpinner.setValueFactory(valueFactory);
        cantidadRepuestoSpinner.setEditable(true);

        cargarFichasActivas();

        fichasComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            cargarDatosFicha(newVal);
        });

        agregarActividadButton.setDisable(true);
        agregarRepuestoButton.setDisable(true);
    }

    private void cargarFichasActivas() {
        try {
            List<FichaMecanica> fichas = fichaMecanicaDAO.getAll().stream().filter(f -> f.getFechaFin() == null).toList();
            fichasComboBox.setItems(FXCollections.observableArrayList(fichas));
        } catch (SQLException e) {
            alert(Alert.AlertType.ERROR, "Error de base de datos", "Ocurrió un error al cargar las fichas activas");
            e.printStackTrace();
        }
    }

    private void cargarDatosFicha(FichaMecanica fichaSeleccionada) {
        try {
            marcaField.clear();
            modeloField.clear();
            anioField.clear();
            tablaActividades.getItems().clear();
            tablaRepuestos.getItems().clear();

            // Se carga la ficha nuevamente para que se actualicen los datos
            this.fichaActual = fichaMecanicaDAO.getById(fichaSeleccionada.getId());

            if (this.fichaActual != null) {
                marcaField.setText(fichaSeleccionada.getVehiculo().getMarca());
                modeloField.setText(fichaSeleccionada.getVehiculo().getModelo());
                anioField.setText(String.valueOf(fichaSeleccionada.getVehiculo().getAnio()));
                actividadesData.addAll(fichaActual.getActividadesRealizadas());
                repuestosData.addAll(fichaActual.getRepuestosEmpleados());
                agregarActividadButton.setDisable(false);
                agregarRepuestoButton.setDisable(false);
            }
            agregarActividadButton.setDisable(this.fichaActual == null);
            agregarRepuestoButton.setDisable(this.fichaActual == null);
        } catch (SQLException e) {
            alert(Alert.AlertType.ERROR, "Error de base de datos", "Ocurrió un error al cargar los datos de la ficha");
            e.printStackTrace();
        }
    }

    @FXML
    private void agregarActividad() {
        String descripcion = descripcionActividadField.getText().trim(); // Obtener descripción y quitar espacios en blanco

        if (descripcion.isEmpty()) {
            alert(Alert.AlertType.ERROR, "Error", "Por favor, ingrese una descripción para la actividad.");
            return;
        }

        String estado = estadoActividadComboBox.getSelectionModel().getSelectedItem();
        if (estado == null) {
            alert(Alert.AlertType.ERROR, "Error", "Por favor, seleccione un estado para la actividad.");
            return;
        }

        if (fichaActual == null) {
            alert(Alert.AlertType.ERROR, "Error", "Por favor, seleccione un cliente primero.");
            return;
        }

        try {
            Actividad nuevaActividad = new Actividad(descripcion, 9, estado, fichaActual.getId());
            actividadDAO.insert(nuevaActividad); // Insertar en la base de datos
            actividadesData.add(nuevaActividad); // Agregar a la tabla
            descripcionActividadField.clear(); // Limpiar el campo de texto
            estadoActividadComboBox.getSelectionModel().clearSelection(); // Limpiar el estado seleccionado del combo
        } catch (SQLException e) {
            alert(Alert.AlertType.ERROR, "Error de base de datos", "Ocurrió un error al agregar la actividad.");
            e.printStackTrace();
        }
    }

    @FXML
    private void agregarRepuesto() {
        String nombre = nombreRepuestoField.getText().trim();
        int cantidad = cantidadRepuestoSpinner.getValue();

        if (nombre.isEmpty() || cantidad == 0) {
            alert(Alert.AlertType.ERROR, "Error", "Por favor, ingrese el nombre y la cantidad del repuesto.");
            return;
        }

        if (fichaActual == null) {
            alert(Alert.AlertType.ERROR, "Error", "Por favor, seleccione un cliente primero.");
            return;
        }

        try {
            Repuesto nuevoRepuesto = new Repuesto(nombre, cantidad, fichaActual.getId());
            repuestoDAO.insert(nuevoRepuesto); // Insertar en la base de datos
            repuestosData.add(nuevoRepuesto); // Agregar a la tabla
            nombreRepuestoField.clear();
            cantidadRepuestoSpinner.getValueFactory().setValue(0);
        } catch (SQLException e) {
            alert(Alert.AlertType.ERROR, "Error de base de datos", "Ocurrió un error al agregar el repuesto.");
            e.printStackTrace();
        }
    }
}
