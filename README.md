# Trabajo Práctico Nº3 - Seminario de Actualización en Programación

## Universidad Siglo 21

**Autor:**

- Matías Sebastián, DNI 31070095, VINF011605

**Profesores:**

- Gutierrez J.M.
- Callejas M.

## Resumen

Este proyecto aborda el Trabajo Práctico Nº3 del Seminario de Actualización en Programación. El enfoque principal es la aplicación del patrón Modelo-Vista-Controlador (MVC) propuesto por Trygve Reenskaug, junto con el patrón de arquitectura Objeto-Acceso a Datos (DAO) para la gestión de datos en una base de datos relacional. El diseño de clases se modela utilizando PlantUML y se complementa con la implementación de al menos tres patrones de diseño adicionales (Singleton, Factory Method, Command, State, Strategy o Decorator).

**Palabras Clave:** MVC, DAO, PlantUML, Patrones de Diseño, Java

## Diseño y Arquitectura

* **Modelo-Vista-Controlador (MVC):** Se aplica este patrón para separar la lógica de negocio (Modelo), la presentación (Vista) y la interacción del usuario (Controlador).
* **Objeto-Acceso a Datos (DAO):** Se utiliza este patrón para abstraer el acceso a la base de datos, proporcionando una interfaz común para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los datos.
* **Patrones de Diseño Adicionales:** Se implementan al menos tres patrones de diseño adicionales para mejorar la modularidad, flexibilidad y mantenibilidad del código. Los patrones específicos utilizados se detallan en la documentación del proyecto.

## Diagrama Entidad-Relación (DER)

El DER se encuentra en el archivo `diagrama_entidad_relacion.puml` y se ha generado utilizando PlantUML. Este diagrama modela la estructura de la base de datos relacional y sus relaciones, reflejando el diseño de clases del modelo.

## Implementación

El proyecto se ha implementado en Java, utilizando las siguientes herramientas y tecnologías:

* **Lenguaje:** Java
* **Modelado:** PlantUML
* **Patrones:** MVC, DAO, Singleton, Factory Method, (otros patrones según implementación)

## Instrucciones de Uso

1. **Clonar el Repositorio:**
   ```bash
   git clone https://github.com/matsebas/sap-tp3
   ```

2. **Configuración del Entorno:**
    * Asegúrate de tener instalado el JDK de Java y PlantUML.
    * Configura la conexión a la base de datos en el archivo de configuración del proyecto.

3. **Generar el DER:**
   ```bash
   plantuml diagrama_entidad_relacion.puml
   ```

4. **Ejecutar la Aplicación:**
   ```bash
   java -jar nombre_del_archivo.jar
   ```

## Referencias

* **Trygve Reenskaug:** [https://folk.universitetetioslo.no/trygver/](https://folk.universitetetioslo.no/trygver/)
* **PlantUML:** [https://plantuml.com/es/](https://plantuml.com/es/)