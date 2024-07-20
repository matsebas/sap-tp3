package com.msebastiao.sap.controller;

import com.msebastiao.sap.dao.FichaMecanicaDAO;
import com.msebastiao.sap.dao.TurnoDAO;
import com.msebastiao.sap.model.FichaMecanica;
import com.msebastiao.sap.model.Turno;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.msebastiao.sap.view.AlertUtil.alert;

public class RegistrarAsistenciaController {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<Turno> tablaTurnos;
    @FXML
    private TableColumn<Turno, String> dniColumn;
    @FXML
    private TableColumn<Turno, String> nombreColumn;
    @FXML
    private TableColumn<Turno, String> apellidoColumn;
    @FXML
    private TableColumn<Turno, String> fechaHoraColumn;
    @FXML
    private TableColumn<Turno, String> estadoColumn;
    @FXML
    private Button confirmarAsistenciaButton;

    private TurnoDAO turnoDAO;
    private ObservableList<Turno> turnosData;

    @FXML
    private void initialize() throws SQLException {
        turnoDAO = new TurnoDAO();
        turnosData = FXCollections.observableArrayList();
        tablaTurnos.setItems(turnosData);

        dniColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitularVehiculo().getDni()));
        nombreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitularVehiculo().getNombre()));
        apellidoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitularVehiculo().getApellido()));
        fechaHoraColumn.setCellValueFactory(cellData -> new SimpleStringProperty(formatearFechaHora(cellData.getValue().getFecha(), cellData.getValue().getHoraInicio())));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
        estadoColumn.setCellFactory(this::crearCeldaEstado);

        // Deshabilitar el botón de confirmar inicialmente
        confirmarAsistenciaButton.setDisable(true);

        // Agregar un listener para habilitar/deshabilitar el botón según la selección
        tablaTurnos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean disableButton = newSelection == null || !newSelection.getEstado().equals("Solicitado");
            confirmarAsistenciaButton.setDisable(disableButton);
        });

        // Cargar los turnos solicitados al iniciar
        cargarTurnosSolicitados();

    }

    private String formatearFechaHora(LocalDate fecha, LocalTime hora) {
        LocalDateTime dateTime = LocalDateTime.of(fecha, hora);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dateTime.format(formatter);
    }

    private TableCell<Turno, String> crearCeldaEstado(TableColumn<Turno, String> column) {
        return new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle(null);
                } else {
                    setText(item);
                    if (item.equals("Solicitado")) {
                        setTextFill(Color.BLACK);
                        setStyle("-fx-background-color: lightblue;");
                    } else if (item.equals("Confirmado")) {
                        setTextFill(Color.WHITE);
                        setStyle("-fx-background-color: green;");
                    } else {
                        setTextFill(Color.BLACK);
                        setStyle("-fx-background-color: gray;");
                    }
                }
            }
        };
    }

    private void cargarTurnosSolicitados() {
        try {
            List<Turno> turnosSolicitados = turnoDAO.getAll().stream()
                    .filter(turno -> "Solicitado".equals(turno.getEstado()) || "Confirmado".equals(turno.getEstado())).toList();
            turnosData.clear();
            turnosData.addAll(turnosSolicitados);
        } catch (SQLException e) {
            alert(Alert.AlertType.ERROR, "Error de base de datos", "Ocurrió un error al cargar los turnos.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String busqueda = searchField.getText();
        try {
            List<Turno> turnosEncontrados = turnoDAO.getAll().stream().filter(turno -> turno.getTitularVehiculo() != null && (turno.getTitularVehiculo().getDni().toLowerCase().contains(busqueda) || turno.getTitularVehiculo().getApellido().toLowerCase().contains(busqueda))).toList();
            turnosData.clear();
            turnosData.addAll(turnosEncontrados);
        } catch (SQLException e) {
            alert(Alert.AlertType.ERROR, "Error de base de datos", "Ocurrió un error al buscar turnos.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleConfirm() {
        Turno turnoSeleccionado = tablaTurnos.getSelectionModel().getSelectedItem();
        if (turnoSeleccionado != null) {
            try {
                turnoSeleccionado.setEstado("Confirmado");
                turnoDAO.update(turnoSeleccionado);

                // Se crea la Ficha Mecánica con los datos del turno confirmado
                FichaMecanicaDAO fichaMecanicaDAO = new FichaMecanicaDAO();
                FichaMecanica fichaMecanica = new FichaMecanica(turnoSeleccionado.getTitularVehiculo(), turnoSeleccionado.getTitularVehiculo().getVehiculos().getFirst(), turnoSeleccionado.getFecha());
                fichaMecanicaDAO.insert(fichaMecanica);

                alert(Alert.AlertType.INFORMATION, "Asistencia confirmada", "La asistencia ha sido confirmada.");
                tablaTurnos.refresh();
            } catch (SQLException e) {
                alert(Alert.AlertType.ERROR, "Error de base de datos", "Ocurrió un error al confirmar la asistencia.");
                e.printStackTrace();
            }
        }
    }
}
