# Seminario de Actualización en Programación, TP Nº3

### Universidad Siglo 21

**Autor:**

- Matias Sebastiao, DNI 31070095, VINF011605

**Profesores:**

- Gutierrez J.M.
- Callejas M.

## Resumen

Este proyecto ha sido desarrollado como parte del trabajo práctico Nº2 para el Seminario de Actualización en
Programación en la Universidad Siglo 21. El objetivo es diseñar y desarrollar los prototipos de interfaz y diagrama de
navegación para un sistema de información destinado a gestionar operaciones en una empresa. El desarrollo se realizó
utilizando JavaFX, una librería gráfica de Java, complementada con PlantUML para el diseño de diagramas.

**Palabras Clave:** plantuml, javafx

## Requisitos

Para ejecutar este proyecto, asegúrate de tener instalado lo siguiente:

- JDK 19 o superior
- JavaFX SDK (puedes descargarlo desde [OpenJFX](https://openjfx.io/))
- IDE de tu preferencia configurado para trabajar con JavaFX (Ejemplo: IntelliJ IDEA, Eclipse)

## Configuración del Proyecto

1. **Descargar JavaFX SDK**: Descarga el SDK de JavaFX desde el sitio oficial y extrae el contenido en una ubicación de
   tu preferencia.

2. **Configuración de IDE**:
    - **IntelliJ IDEA**:
        - Crea un nuevo proyecto de Java.
        - En `Project Structure` -> `Libraries`, agrega el directorio `lib` del JavaFX SDK descargado como una nueva
          biblioteca.
        - En la configuración de ejecución, añade el siguiente argumento VM para incorporar los módulos de JavaFX:
          ```
          --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
          ```
    - **Eclipse**:
        - Crea un proyecto de Java.
        - Ve a `Project Properties` -> `Java Build Path` y añade el directorio `lib` del JavaFX SDK como biblioteca
          externa.
        - En la configuración de ejecución, añade los mismos argumentos VM que para IntelliJ.

3. **Clonar el repositorio**:
   ```
   git clone [url-del-repositorio]
   ```

## Ejecución del Proyecto

Para ejecutar el proyecto:

1. Abre el proyecto clonado en tu IDE.
2. Navega al archivo `HomeScreen.java`.
3. Ejecuta el archivo usando el IDE para iniciar la aplicación.

El sistema mostrará una interfaz gráfica que permite navegar entre diferentes funciones y módulos del sistema de
información diseñado para la empresa.