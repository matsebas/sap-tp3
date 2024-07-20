package com.msebastiao.sap.controller;

import com.msebastiao.sap.dao.TitularVehiculoDAO;
import com.msebastiao.sap.dao.TurnoDAO;
import com.msebastiao.sap.model.TitularVehiculo;
import com.msebastiao.sap.model.Turno;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static com.msebastiao.sap.view.AlertUtil.alert;

public class SolicitarTurnoController {

    @FXML
    public Button solicitarTurnoButton;
    @FXML
    private TableView<Turno> turnosTable;
    @FXML
    private TableColumn<Turno, LocalDate> fechaColumn;
    @FXML
    private TableColumn<Turno, LocalTime> horaInicioColumn;
    @FXML
    private TableColumn<Turno, LocalTime> horaFinColumn;
    @FXML
    private TableColumn<Turno, String> estadoColumn;
    @FXML
    private TextField dniField;

    @FXML
    public void initialize() {
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        horaInicioColumn.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));
        horaFinColumn.setCellValueFactory(new PropertyValueFactory<>("horaFin"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
        solicitarTurnoButton.setDisable(true);

        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
        estadoColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Turno, String> call(TableColumn<Turno, String> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            setText(item);
                            if (item.equals("Disponible")) {
                                setTextFill(Color.BLACK); // Texto en negro para mejor contraste
                                setStyle("-fx-background-color: lightgreen;");
                            } else {
                                setTextFill(Color.BLACK);
                                setStyle("-fx-background-color: lightcoral;");
                            }
                        } else {
                            setText(null);
                            setStyle(null);
                        }
                    }
                };
            }
        });
        cargarTurnos();
    }

    private void cargarTurnos() {

        List<Turno> turnos = Collections.emptyList();
        try {
            TurnoDAO turnoDAO = new TurnoDAO();
            turnos = turnoDAO.getAll();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Turnos");
            alert.setHeaderText(null);
            alert.setContentText("No se encontraron turnos disponibles");
            alert.showAndWait();
        }
        turnosTable.getItems().setAll(turnos);
    }

    @FXML
    private void verificarEstadoTurno(MouseEvent event) {
        if (turnosTable.getSelectionModel().getSelectedItem() != null) {
            Turno turnoSeleccionado = turnosTable.getSelectionModel().getSelectedItem();
            solicitarTurnoButton.setDisable(!turnoSeleccionado.getEstado().equals("Disponible"));
        }
    }


    @FXML
    private void handleSolicitarTurno() {
        try {
            Turno selectedTurno = turnosTable.getSelectionModel().getSelectedItem();
            if (selectedTurno == null) {
                alert(Alert.AlertType.ERROR, "Error", "Por favor, seleccione un turno disponible.");
                return;
            }
            String dni = dniField.getText();
            TitularVehiculoDAO titularVehiculoDAO = new TitularVehiculoDAO();
            TitularVehiculo titular = titularVehiculoDAO.getByDni(dni);

            if (titular == null) {
                alert(Alert.AlertType.WARNING, "ERROR", "El Titular no existe o no fue encontrado con el DNI ingresado.");
                return;
            }

            selectedTurno.setEstado("Solicitado");
            selectedTurno.setTitularVehiculo(titular);
            TurnoDAO turnoDAO = new TurnoDAO();
            turnoDAO.update(selectedTurno);

            cargarTurnos();

            alert(Alert.AlertType.INFORMATION, "Turno Solicitado", "El turno ha sido solicitado exitosamente.");

        } catch (SQLException e) {
            alert(Alert.AlertType.ERROR, "Error", "No se pudo solicitar el turno por un error inesperado.");
            e.printStackTrace();
        }
    }
}
