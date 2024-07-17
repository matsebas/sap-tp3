package com.msebastiao.sap.commands;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

/**
 * El patrón Command convierte las solicitudes en objetos, permitiendo parametrizar los métodos con diferentes
 * solicitudes, retrasar o poner en cola la ejecución de una solicitud y soportar operaciones que se pueden deshacer.
 * En este caso se usa para abrir una nueva vista en la interfaz de usuario con el nombre de la vista dada.
 */
public class OpenViewCommand {

    private final TabPane tabPane;
    private final String title;
    private final Pane view;

    public OpenViewCommand(TabPane tabPane, String title, Pane view) {
        this.tabPane = tabPane;
        this.title = title;
        this.view = view;
    }

    public void execute() {
        for (Tab tab : tabPane.getTabs()) {
            if (tab.getText().equals(title)) {
                tabPane.getSelectionModel().select(tab);
                return;
            }
        }
        Tab tab = new Tab(title);
        tab.setContent(view);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }
}
