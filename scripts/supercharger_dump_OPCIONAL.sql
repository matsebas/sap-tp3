-- MySQL dump 10.13  Distrib 8.3.0, for macos14.2 (arm64)
--
-- Host: 127.0.0.1    Database: supercharger
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `actividades`
--

DROP TABLE IF EXISTS `actividades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `actividades` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `descripcion` varchar(255) NOT NULL,
                               `tiempo_empleado` int NOT NULL,
                               `estado` varchar(50) NOT NULL,
                               `ficha_mecanica_id` int DEFAULT NULL,
                               PRIMARY KEY (`id`),
                               KEY `ficha_mecanica_id` (`ficha_mecanica_id`),
                               CONSTRAINT `actividades_ibfk_1` FOREIGN KEY (`ficha_mecanica_id`) REFERENCES `fichas_mecanicas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actividades`
--

LOCK TABLES `actividades` WRITE;
/*!40000 ALTER TABLE `actividades` DISABLE KEYS */;
INSERT INTO `actividades` (`id`, `descripcion`, `tiempo_empleado`, `estado`, `ficha_mecanica_id`) VALUES (1,'Mecánica general',9,'Finalizada',1),(2,'Cambio de 4 llantas',9,'En curso',1),(3,'Cambio de aceite',9,'En curso',2),(4,'Cambio de filtro de aire',9,'En curso',2),(5,'Reemplazo bomba nafta',9,'Cancelada',1);
/*!40000 ALTER TABLE `actividades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fichas_mecanicas`
--

DROP TABLE IF EXISTS `fichas_mecanicas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fichas_mecanicas` (
                                    `id` int NOT NULL AUTO_INCREMENT,
                                    `mecanico_id` int DEFAULT NULL,
                                    `titular_vehiculo_id` int DEFAULT NULL,
                                    `vehiculo_id` int DEFAULT NULL,
                                    `fecha_inicio` date NOT NULL,
                                    `fecha_fin` date DEFAULT NULL,
                                    `estado` varchar(50) NOT NULL,
                                    PRIMARY KEY (`id`),
                                    KEY `mecanico_id` (`mecanico_id`),
                                    KEY `titular_vehiculo_id` (`titular_vehiculo_id`),
                                    KEY `vehiculo_id` (`vehiculo_id`),
                                    CONSTRAINT `fichas_mecanicas_ibfk_1` FOREIGN KEY (`mecanico_id`) REFERENCES `mecanicos` (`id`),
                                    CONSTRAINT `fichas_mecanicas_ibfk_2` FOREIGN KEY (`titular_vehiculo_id`) REFERENCES `titular_vehiculo` (`id`),
                                    CONSTRAINT `fichas_mecanicas_ibfk_3` FOREIGN KEY (`vehiculo_id`) REFERENCES `vehiculos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fichas_mecanicas`
--

LOCK TABLES `fichas_mecanicas` WRITE;
/*!40000 ALTER TABLE `fichas_mecanicas` DISABLE KEYS */;
INSERT INTO `fichas_mecanicas` (`id`, `mecanico_id`, `titular_vehiculo_id`, `vehiculo_id`, `fecha_inicio`, `fecha_fin`, `estado`) VALUES (1,NULL,2,2,'2024-08-30',NULL,'Creada'),(2,NULL,1,1,'2024-08-20',NULL,'Pendiente');
/*!40000 ALTER TABLE `fichas_mecanicas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informes`
--

DROP TABLE IF EXISTS `informes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `informes` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `fecha` date NOT NULL,
                            `contenido` text NOT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informes`
--

LOCK TABLES `informes` WRITE;
/*!40000 ALTER TABLE `informes` DISABLE KEYS */;
/*!40000 ALTER TABLE `informes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mecanicos`
--

DROP TABLE IF EXISTS `mecanicos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mecanicos` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `nombre` varchar(100) NOT NULL,
                             `apellido` varchar(100) NOT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mecanicos`
--

LOCK TABLES `mecanicos` WRITE;
/*!40000 ALTER TABLE `mecanicos` DISABLE KEYS */;
INSERT INTO `mecanicos` (`id`, `nombre`, `apellido`) VALUES (1,'Carlos','Lopez'),(2,'Pedro','Martinez'),(3,'Carlos','Lopez'),(4,'Pedro','Martinez');
/*!40000 ALTER TABLE `mecanicos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repuestos`
--

DROP TABLE IF EXISTS `repuestos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `repuestos` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `nombre` varchar(100) NOT NULL,
                             `cantidad` int NOT NULL,
                             `ficha_mecanica_id` int DEFAULT NULL,
                             PRIMARY KEY (`id`),
                             KEY `ficha_mecanica_id` (`ficha_mecanica_id`),
                             CONSTRAINT `repuestos_ibfk_1` FOREIGN KEY (`ficha_mecanica_id`) REFERENCES `fichas_mecanicas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repuestos`
--

LOCK TABLES `repuestos` WRITE;
/*!40000 ALTER TABLE `repuestos` DISABLE KEYS */;
INSERT INTO `repuestos` (`id`, `nombre`, `cantidad`, `ficha_mecanica_id`) VALUES (1,'llantas de 17p de aluminio',4,1),(2,'frenos',2,1),(3,'aceite',1,1),(4,'Aceite Elaion Plus',1,2),(5,'Tapón de aceite',1,2),(6,'Filtro de aire pro',1,2);
/*!40000 ALTER TABLE `repuestos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `titular_vehiculo`
--

DROP TABLE IF EXISTS `titular_vehiculo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `titular_vehiculo` (
                                    `id` int NOT NULL AUTO_INCREMENT,
                                    `dni` varchar(20) NOT NULL,
                                    `nombre` varchar(100) NOT NULL,
                                    `apellido` varchar(100) NOT NULL,
                                    PRIMARY KEY (`id`),
                                    UNIQUE KEY `dni` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `titular_vehiculo`
--

LOCK TABLES `titular_vehiculo` WRITE;
/*!40000 ALTER TABLE `titular_vehiculo` DISABLE KEYS */;
INSERT INTO `titular_vehiculo` (`id`, `dni`, `nombre`, `apellido`) VALUES (1,'12345678','Juan','Perez'),(2,'87654321','Maria','Gonzalez');
/*!40000 ALTER TABLE `titular_vehiculo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `turnos`
--

DROP TABLE IF EXISTS `turnos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `turnos` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `fecha` date NOT NULL,
                          `hora_inicio` time NOT NULL,
                          `hora_fin` time NOT NULL,
                          `estado` varchar(50) NOT NULL,
                          `titular_vehiculo_id` int DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          KEY `titular_vehiculo_id` (`titular_vehiculo_id`),
                          CONSTRAINT `turnos_ibfk_1` FOREIGN KEY (`titular_vehiculo_id`) REFERENCES `titular_vehiculo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `turnos`
--

LOCK TABLES `turnos` WRITE;
/*!40000 ALTER TABLE `turnos` DISABLE KEYS */;
INSERT INTO `turnos` (`id`, `fecha`, `hora_inicio`, `hora_fin`, `estado`, `titular_vehiculo_id`) VALUES (1,'2024-08-20','08:00:00','10:00:00','Disponible',NULL),(2,'2024-08-20','10:00:00','12:00:00','Disponible',NULL),(3,'2024-08-20','12:00:00','14:00:00','Confirmado',1),(4,'2024-08-20','14:00:00','16:00:00','Disponible',NULL),(5,'2024-08-20','16:00:00','18:00:00','Disponible',NULL),(6,'2024-08-21','08:00:00','10:00:00','Disponible',NULL),(7,'2024-08-21','10:00:00','12:00:00','Disponible',NULL),(8,'2024-08-21','12:00:00','14:00:00','Disponible',NULL),(9,'2024-08-21','14:00:00','16:00:00','Disponible',NULL),(10,'2024-08-21','16:00:00','18:00:00','Disponible',NULL),(11,'2024-08-22','08:00:00','10:00:00','Disponible',NULL),(12,'2024-08-22','10:00:00','12:00:00','Disponible',NULL),(13,'2024-08-22','12:00:00','14:00:00','Disponible',NULL),(14,'2024-08-22','14:00:00','16:00:00','Disponible',NULL),(15,'2024-08-22','16:00:00','18:00:00','Disponible',NULL),(16,'2024-08-23','08:00:00','10:00:00','Disponible',NULL),(17,'2024-08-23','10:00:00','12:00:00','Disponible',NULL),(18,'2024-08-23','12:00:00','14:00:00','Disponible',NULL),(19,'2024-08-23','14:00:00','16:00:00','Disponible',NULL),(20,'2024-08-23','16:00:00','18:00:00','Disponible',NULL),(21,'2024-08-26','08:00:00','10:00:00','Disponible',NULL),(22,'2024-08-26','10:00:00','12:00:00','Disponible',NULL),(23,'2024-08-26','12:00:00','14:00:00','Disponible',NULL),(24,'2024-08-26','14:00:00','16:00:00','Disponible',NULL),(25,'2024-08-26','16:00:00','18:00:00','Disponible',NULL),(26,'2024-08-27','08:00:00','10:00:00','Disponible',NULL),(27,'2024-08-27','10:00:00','12:00:00','Disponible',NULL),(28,'2024-08-27','12:00:00','14:00:00','Disponible',NULL),(29,'2024-08-27','14:00:00','16:00:00','Disponible',NULL),(30,'2024-08-27','16:00:00','18:00:00','Disponible',NULL),(31,'2024-08-28','08:00:00','10:00:00','Disponible',NULL),(32,'2024-08-28','10:00:00','12:00:00','Disponible',NULL),(33,'2024-08-28','12:00:00','14:00:00','Disponible',NULL),(34,'2024-08-28','14:00:00','16:00:00','Disponible',NULL),(35,'2024-08-28','16:00:00','18:00:00','Disponible',NULL),(36,'2024-08-29','08:00:00','10:00:00','Disponible',NULL),(37,'2024-08-29','10:00:00','12:00:00','Disponible',NULL),(38,'2024-08-29','12:00:00','14:00:00','Disponible',NULL),(39,'2024-08-29','14:00:00','16:00:00','Disponible',NULL),(40,'2024-08-29','16:00:00','18:00:00','Disponible',NULL),(41,'2024-08-30','08:00:00','10:00:00','Disponible',NULL),(42,'2024-08-30','10:00:00','12:00:00','Disponible',NULL),(43,'2024-08-30','12:00:00','14:00:00','Disponible',NULL),(44,'2024-08-30','14:00:00','16:00:00','Confirmado',2),(45,'2024-08-30','16:00:00','18:00:00','Disponible',NULL);
/*!40000 ALTER TABLE `turnos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehiculos`
--

DROP TABLE IF EXISTS `vehiculos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehiculos` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `marca` varchar(50) NOT NULL,
                             `modelo` varchar(50) NOT NULL,
                             `anio` int NOT NULL,
                             `titular_vehiculo_id` int DEFAULT NULL,
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `modelo` (`modelo`),
                             KEY `titular_vehiculo_id` (`titular_vehiculo_id`),
                             CONSTRAINT `vehiculos_ibfk_1` FOREIGN KEY (`titular_vehiculo_id`) REFERENCES `titular_vehiculo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehiculos`
--

LOCK TABLES `vehiculos` WRITE;
/*!40000 ALTER TABLE `vehiculos` DISABLE KEYS */;
INSERT INTO `vehiculos` (`id`, `marca`, `modelo`, `anio`, `titular_vehiculo_id`) VALUES (1,'Toyota','Corolla',2019,1),(2,'Honda','Civic',2018,2);
/*!40000 ALTER TABLE `vehiculos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'supercharger'
--
/*!50003 DROP PROCEDURE IF EXISTS `insert_turnos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `insert_turnos`()
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
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-20 20:18:27
