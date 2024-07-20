# Trabajo Práctico Nº3 - Seminario de Actualización en Programación

## Universidad Siglo 21

**Autor:**

- Matías Sebastián, DNI 31070095, VINF011605

**Profesores:**

- Gutierrez J.M.
- Callejas M.

## Resumen

Este proyecto aborda el Trabajo Práctico Nº3 del Seminario de Actualización en Programación. El enfoque principal es la
aplicación del patrón Modelo-Vista-Controlador (MVC) propuesto por Trygve Reenskaug, junto con el patrón de arquitectura
Objeto-Acceso a Datos (DAO) para la gestión de datos en una base de datos relacional. El diseño de clases se modela
utilizando PlantUML y se complementa con la implementación de al menos tres patrones de diseño adicionales (Singleton,
Factory Method, Command, State, Strategy o Decorator).

**Palabras Clave:** MVC, DAO, PlantUML, Patrones de Diseño, Java

## Diseño y Arquitectura

* **Modelo-Vista-Controlador (MVC):** Se aplica este patrón para separar la lógica de negocio (Modelo), la
  presentación (Vista) y la interacción del usuario (Controlador).
* **Objeto-Acceso a Datos (DAO):** Se utiliza este patrón para abstraer el acceso a la base de datos, proporcionando una
  interfaz común para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los datos.
* **Patrones de Diseño Adicionales:** Se implementan al menos tres patrones de diseño adicionales para mejorar la
  modularidad, flexibilidad y mantenibilidad del código. Los patrones específicos utilizados se detallan en la
  documentación del proyecto.

## Diagrama Entidad-Relación (DER)

El DER se encuentra en el archivo `der.puml` y se ha generado utilizando PlantUML. Este diagrama modela la estructura de
la base de datos relacional y sus relaciones, reflejando el diseño de clases del modelo.

## Implementación

El proyecto se ha implementado en Java, utilizando las siguientes herramientas y tecnologías:

* **Lenguaje:** Java
* **Modelado:** PlantUML
* **Patrones:** MVC, DAO, Singleton, Factory Method, Command

## Instrucciones de Uso

1. **Clonar el Repositorio:**
   ```bash
   git clone https://github.com/matsebas/sap-tp3
   ```

2. **Configuración del Entorno:**
    * Asegúrate de tener instalado el JDK 21 de Java y PlantUML.
    * Configura la conexión a la base de datos en el archivo de configuración del proyecto.

3. **Compilar el proyecto utilizando Maven:**
    ```sh
    mvn clean install
    ```

4. **Ejecutar la aplicación**

   * Asegúrate de que tu base de datos MySQL esté en funcionamiento y configurada correctamente.

   * Ejecutá la aplicación utilizando Maven:
     ```sh
     mvn javafx:run
     ```
       Esto compilará y ejecutará la aplicación JavaFX.

## Estructura del proyecto

- `src/main/java`: Contiene el código fuente principal de la aplicación.
- `src/main/resources`: Contiene los archivos FXML y otros recursos.
- `src/test/java`: Contiene las pruebas unitarias.

## Dependencias principales

- JavaFX 21 (controles y fxml)
- MySQL Connector/J 8.2.0

## Configuración de la base de datos

Asegúrate de que la configuración de la base de datos en tu clase `DatabaseConnection` apunte a tu instancia de MySQL.
Aquí tienes un ejemplo de la configuración:

```java
private void crearConexion() throws SQLException {
    String url = "jdbc:mysql://localhost:3306/glucoforecast";
    String usuario = "root";
    String contrasena = "P4ssw0rd!";
    conexion = DriverManager.getConnection(url, usuario, contrasena);
}
```

## Referencias

* **Trygve Reenskaug:** [https://folk.universitetetioslo.no/trygver/](https://folk.universitetetioslo.no/trygver/)
* **PlantUML:** [https://plantuml.com/es/](https://plantuml.com/es/)