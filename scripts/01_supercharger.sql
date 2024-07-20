USE supercharger;

-- Crear la tabla TitularVehiculo
CREATE TABLE titular_vehiculo
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    dni      VARCHAR(20)  NOT NULL UNIQUE,
    nombre   VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL
);

-- Crear la tabla Vehiculos
CREATE TABLE vehiculos
(
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    marca               VARCHAR(50) NOT NULL,
    modelo              VARCHAR(50) NOT NULL UNIQUE,
    año                 INT         NOT NULL,
    titular_vehiculo_id INT,
    FOREIGN KEY (titular_vehiculo_id) REFERENCES titular_vehiculo (id)
);

-- Crear la tabla Mecanicos
CREATE TABLE mecanicos
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    nombre   VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL
);

-- Crear la tabla Turnos
CREATE TABLE turnos
(
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    fecha               DATE        NOT NULL,
    hora_inicio         TIME        NOT NULL,
    hora_fin            TIME        NOT NULL,
    estado              VARCHAR(50) NOT NULL,
    titular_vehiculo_id INT,
    FOREIGN KEY (titular_vehiculo_id) REFERENCES titular_vehiculo (id)
);

-- Crear la tabla FichasMecanicas
CREATE TABLE fichas_mecanicas
(
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    mecanico_id         INT,
    titular_vehiculo_id INT,
    vehiculo_id         INT,
    fecha_inicio        DATE        NOT NULL,
    fecha_fin           DATE,
    estado              VARCHAR(50) NOT NULL,
    FOREIGN KEY (mecanico_id) REFERENCES mecanicos (id),
    FOREIGN KEY (titular_vehiculo_id) REFERENCES titular_vehiculo (id),
    FOREIGN KEY (vehiculo_id) REFERENCES vehiculos (id)
);

-- Crear la tabla Actividades
CREATE TABLE actividades
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    descripcion       VARCHAR(255) NOT NULL,
    tiempo_empleado   INT          NOT NULL,
    estado            VARCHAR(50)  NOT NULL,
    ficha_mecanica_id INT,
    FOREIGN KEY (ficha_mecanica_id) REFERENCES fichas_mecanicas (id)
);

-- Crear la tabla Repuestos
CREATE TABLE repuestos
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    nombre            VARCHAR(100) NOT NULL,
    cantidad          INT          NOT NULL,
    ficha_mecanica_id INT,
    FOREIGN KEY (ficha_mecanica_id) REFERENCES fichas_mecanicas (id)
);

-- Crear la tabla Informes
CREATE TABLE informes
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    fecha     DATE NOT NULL,
    contenido TEXT NOT NULL
);

-- Insertar datos en TitularVehiculo
INSERT INTO titular_vehiculo (dni, nombre, apellido)
VALUES ('12345678', 'Juan', 'Perez'),
       ('87654321', 'Maria', 'Gonzalez');

-- Insertar datos en Vehiculos
INSERT INTO vehiculos (marca, modelo, año, titular_vehiculo_id)
VALUES ('Toyota', 'Corolla', 2019, 1),
       ('Honda', 'Civic', 2018, 2);

-- Insertar datos en Mecanicos
INSERT INTO mecanicos (nombre, apellido)
VALUES ('Carlos', 'Lopez'),
       ('Pedro', 'Martinez');

-- Insertar datos en Mecanicos
INSERT INTO mecanicos (nombre, apellido)
VALUES ('Carlos', 'Lopez'),
       ('Pedro', 'Martinez');

-- Eliminación del procedimiento almacenado si existe
DROP PROCEDURE IF EXISTS insert_turnos;

DELIMITER $$

-- Creación del procedimiento almacenado
CREATE PROCEDURE insert_turnos()
BEGIN
    DECLARE v_start_date DATE;
    DECLARE v_end_date DATE;
    DECLARE v_date DATE;
    DECLARE v_time TIME;
    DECLARE v_next_time TIME;
    DECLARE v_day_of_week INT;

    -- Calcular las fechas para el próximo mes
    SET v_start_date = DATE_ADD(CURDATE(), INTERVAL 1 MONTH);
    SET v_end_date = LAST_DAY(v_start_date);

    SET v_date = v_start_date;

    -- Generar turnos para cada día de la semana, de lunes a viernes, de 8:00 a 18:00 con duración de 2 horas
    WHILE v_date <= v_end_date
        DO
            SET v_day_of_week = DAYOFWEEK(v_date);
            IF v_day_of_week BETWEEN 2 AND 6 THEN
                SET v_time = '08:00:00';
                WHILE v_time < '18:00:00'
                    DO
                        SET v_next_time = ADDTIME(v_time, '02:00:00');
                        INSERT INTO turnos (fecha, hora_inicio, hora_fin, estado, titular_vehiculo_id)
                        VALUES (v_date, v_time, v_next_time, 'Disponible', NULL);
                        SET v_time = v_next_time;
                    END WHILE;
            END IF;
            SET v_date = DATE_ADD(v_date, INTERVAL 1 DAY);
        END WHILE;
END$$

DELIMITER ;

-- Llamar al procedimiento almacenado para insertar los turnos
CALL insert_turnos();

