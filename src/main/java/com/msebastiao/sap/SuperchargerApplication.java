package com.msebastiao.sap;

import com.msebastiao.sap.commands.OpenViewCommand;
import com.msebastiao.sap.view.ViewFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SuperchargerApplication extends Application {
    private final TabPane tabPane = new TabPane();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Supercharger SRL - Home");
        BorderPane root = new BorderPane();
        MenuBar menuBar = new MenuBar();

        // Menús principales
        Menu inicioMenu = new Menu("Inicio");
        Menu clientesMenu = new Menu("Clientes");
        Menu recepcionMenu = new Menu("Recepción");
        Menu tallerMenu = new Menu("Taller");
        Menu operacionesMenu = new Menu("Operaciones");
        Menu administracionMenu = new Menu("Administración");

        // Elementos para el menú Clientes
        MenuItem solicitarTurnoItem = new MenuItem("Solicitar Turno");
        solicitarTurnoItem.setOnAction(e -> openView("Clientes - Solicitar Turno", "SolicitarTurnoView"));
        clientesMenu.getItems().add(solicitarTurnoItem);
        MenuItem consultarTurnosItem = new MenuItem("Consultar Turnos");
        solicitarTurnoItem.setOnAction(e -> openView("Clientes - Consultar Turnos", "ConsultarTurnosView"));
        clientesMenu.getItems().add(consultarTurnosItem);

        // Elementos para el menú Recepción
        MenuItem registrarAsistenciaItem = new MenuItem("Registrar Asistencia");
        registrarAsistenciaItem.setOnAction(e -> openView("Recepción - Registrar Asistencia", "RegistrarAsistenciaView"));
        recepcionMenu.getItems().add(registrarAsistenciaItem);
        MenuItem gestionarTurnos = new MenuItem("Gestionar Turnos");
        gestionarTurnos.setDisable(true);
        recepcionMenu.getItems().add(gestionarTurnos);

        // Elementos para el menú Taller
        MenuItem realizarMantenimientoItem = new MenuItem("Realizar Mantenimiento");
        realizarMantenimientoItem.setOnAction(e -> openView("Taller - Realizar Mantenimiento", "RealizarMantenimientoView"));
        tallerMenu.getItems().add(realizarMantenimientoItem);
        MenuItem consultarTareas = new MenuItem("Consultar Tareas Pendientes");
        consultarTareas.setDisable(true);
        tallerMenu.getItems().add(consultarTareas);

        // Elementos para el menú Operaciones
        MenuItem supervisarTareasItem = new MenuItem("Supervisar Tareas");
        supervisarTareasItem.setOnAction(e -> openView("Operaciones - Supervisar Tareas", "SupervisarTareasView"));
        operacionesMenu.getItems().add(supervisarTareasItem);
        MenuItem generarReportes = new MenuItem("Generar Reportes");
        generarReportes.setDisable(true);
        operacionesMenu.getItems().add(generarReportes);

        // Elementos para el menú Administración
        MenuItem gestionarConvenios = new MenuItem("Gestionar Convenios");
        gestionarConvenios.setDisable(true);
        MenuItem configurarSistema = new MenuItem("Configurar Sistema");
        configurarSistema.setDisable(true);
        MenuItem administrarUsuarios = new MenuItem("Administrar Usuarios");
        administrarUsuarios.setDisable(true);
        MenuItem generarInformeItem = new MenuItem("Emitir Informes Mensuales");
        administracionMenu.getItems().addAll(gestionarConvenios, configurarSistema, administrarUsuarios);
        generarInformeItem.setOnAction(e -> openView("Administración - Emitir Informes Mensuales", "GenerarInformeMensualView"));
        administracionMenu.getItems().add(generarInformeItem);

        // Agregando menús a la barra de menús
        menuBar.getMenus().addAll(inicioMenu, clientesMenu, recepcionMenu, tallerMenu, operacionesMenu, administracionMenu);

        root.setTop(menuBar);
        root.setCenter(tabPane);

        Scene scene = new Scene(root, 630, 320);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openView(String title, String viewFXML) {
        Pane view = (Pane) ViewFactory.createView(viewFXML);
        OpenViewCommand command = new OpenViewCommand(tabPane, title, view);
        command.execute();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
