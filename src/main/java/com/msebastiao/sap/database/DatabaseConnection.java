package com.msebastiao.sap.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que gestiona la conexión a la base de datos utilizando el patrón Singleton.
 * Proporciona un único punto de acceso a la conexión y asegura que solo se cree una instancia de la conexión.
 */
public class DatabaseConnection {
    /**
     * Instancia única de la clase {@link DatabaseConnection}.
     */
    private static DatabaseConnection instance;
    /**
     * Conexión a la base de datos.
     */
    private Connection connection;

    /**
     * Constructor privado para prevenir la creación de instancias desde fuera de la clase.
     * Establece la conexión a la base de datos al crear la instancia.
     *
     * @throws SQLException si ocurre un error al establecer la conexión.
     */
    private DatabaseConnection() throws SQLException {
        createConnection();
    }

    /**
     * Crea una nueva conexión a la base de datos.
     *
     * @throws SQLException si ocurre un error al establecer la conexión.
     */
    private void createConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/supercharger";
        String usuario = "root";
        String contrasena = "P4ssw0rd!";
        connection = DriverManager.getConnection(url, usuario, contrasena);
    }

    /**
     * Obtiene la instancia única de la clase {@link DatabaseConnection}.
     * Si la instancia no existe o la conexión está cerrada, se crea una nueva instancia.
     *
     * @return La instancia única de {@link DatabaseConnection}.
     * @throws SQLException Si ocurre un error al crear la conexión.
     */
    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null || instance.connection.isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * Obtiene la conexión a la base de datos.
     * Si la conexión no existe o está cerrada, se crea una nueva conexión.
     *
     * @return La conexión a la base de datos.
     * @throws SQLException Si ocurre un error al crear la conexión.
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            createConnection();
        }
        return connection;
    }
}