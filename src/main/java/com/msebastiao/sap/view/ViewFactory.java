package com.msebastiao.sap.view;

import javafx.scene.Parent;

/**
 * El patrón Factory Method define una interfaz para crear un objeto, pero deja que las subclases decidan qué clase
 * instanciar.
 * Se usa para crear las vistas basadas en FXML.
 */
public class ViewFactory {

    /**
     * Crea una vista dada su nombre y la devuelve en un objeto de tipo Parent para ser renderizado en la interfaz
     * de usuario.
     *
     * @param viewName nombre de la vista
     * @return objeto de tipo {@link Parent}
     */
    public static Parent createView(String viewName) {
        return ViewManager.getInstance().getView(viewName);
    }
}
