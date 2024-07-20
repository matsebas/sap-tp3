package com.msebastiao.sap.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class ViewManager {

    private static ViewManager instance;

    /**
     * Devuelve la instancia de la clase ViewManager aplicando el patrón Singleton
     */
    public static ViewManager getInstance() {
        if (instance == null) {
            instance = new ViewManager();
        }
        return instance;
    }

    public Parent getView(String viewName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(viewName + ".fxml"));
            return fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
