-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema bimestral
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema bimestral
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bimestral` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `bimestral` ;

-- -----------------------------------------------------
-- Table `bimestral`.`persona`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bimestral`.`persona` (
  `CEDULA` VARCHAR(255) NOT NULL,
  `DTYPE` VARCHAR(31) NULL DEFAULT NULL,
  `APELLIDOS` VARCHAR(255) NULL DEFAULT NULL,
  `EMAIL` VARCHAR(255) NULL DEFAULT NULL,
  `NOMBRES` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`CEDULA`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bimestral`.`bodeguero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bimestral`.`bodeguero` (
  `CEDULA` VARCHAR(255) NOT NULL,
  `LOCAL` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`CEDULA`),
  CONSTRAINT `FK_BODEGUERO_CEDULA`
    FOREIGN KEY (`CEDULA`)
    REFERENCES `bimestral`.`persona` (`CEDULA`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bimestral`.`cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bimestral`.`cliente` (
  `CEDULA` VARCHAR(255) NOT NULL,
  `CELULAR` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`CEDULA`),
  CONSTRAINT `FK_CLIENTE_CEDULA`
    FOREIGN KEY (`CEDULA`)
    REFERENCES `bimestral`.`persona` (`CEDULA`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bimestral`.`direccion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bimestral`.`direccion` (
  `CODIGO` VARCHAR(255) NOT NULL,
  `ACTUAL` TINYINT(1) NULL DEFAULT '0',
  `CALLE1` VARCHAR(255) NULL DEFAULT NULL,
  `CALLE2` VARCHAR(255) NULL DEFAULT NULL,
  `REFERENCIA` VARCHAR(255) NULL DEFAULT NULL,
  `CLIENTE_CEDULA` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`CODIGO`),
  INDEX `FK_DIRECCION_CLIENTE_CEDULA` (`CLIENTE_CEDULA` ASC) VISIBLE,
  CONSTRAINT `FK_DIRECCION_CLIENTE_CEDULA`
    FOREIGN KEY (`CLIENTE_CEDULA`)
    REFERENCES `bimestral`.`persona` (`CEDULA`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bimestral`.`empleado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bimestral`.`empleado` (
  `CEDULA` VARCHAR(255) NOT NULL,
  `CIUDAD` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`CEDULA`),
  CONSTRAINT `FK_EMPLEADO_CEDULA`
    FOREIGN KEY (`CEDULA`)
    REFERENCES `bimestral`.`persona` (`CEDULA`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bimestral`.`paquete`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bimestral`.`paquete` (
  `IDPAQ` INT NOT NULL AUTO_INCREMENT,
  `ALTO` INT NULL DEFAULT NULL,
  `CODIGO` VARCHAR(255) NULL DEFAULT NULL,
  `DESCRIPCION` VARCHAR(255) NULL DEFAULT NULL,
  `PESO` INT NULL DEFAULT NULL,
  `CLIENTE_CEDULA` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`IDPAQ`),
  INDEX `FK_PAQUETE_CLIENTE_CEDULA` (`CLIENTE_CEDULA` ASC) VISIBLE,
  CONSTRAINT `FK_PAQUETE_CLIENTE_CEDULA`
    FOREIGN KEY (`CLIENTE_CEDULA`)
    REFERENCES `bimestral`.`persona` (`CEDULA`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bimestral`.`entrega`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bimestral`.`entrega` (
  `CODIGO` VARCHAR(255) NOT NULL,
  `FECHA` DATE NULL DEFAULT NULL,
  `OBSERVACION` VARCHAR(255) NULL DEFAULT NULL,
  `PAQUETE_IDPAQ` INT NULL DEFAULT NULL,
  PRIMARY KEY (`CODIGO`),
  INDEX `FK_ENTREGA_PAQUETE_IDPAQ` (`PAQUETE_IDPAQ` ASC) VISIBLE,
  CONSTRAINT `FK_ENTREGA_PAQUETE_IDPAQ`
    FOREIGN KEY (`PAQUETE_IDPAQ`)
    REFERENCES `bimestral`.`paquete` (`IDPAQ`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bimestral`.`estado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bimestral`.`estado` (
  `TIPO` INT NOT NULL AUTO_INCREMENT,
  `ESTADO` VARCHAR(255) NULL DEFAULT NULL,
  `FECHA` DATE NULL DEFAULT NULL,
  `OBSERVACION` VARCHAR(255) NULL DEFAULT NULL,
  `PAQUETE_IDPAQ` INT NULL DEFAULT NULL,
  PRIMARY KEY (`TIPO`),
  INDEX `FK_ESTADO_PAQUETE_IDPAQ` (`PAQUETE_IDPAQ` ASC) VISIBLE,
  CONSTRAINT `FK_ESTADO_PAQUETE_IDPAQ`
    FOREIGN KEY (`PAQUETE_IDPAQ`)
    REFERENCES `bimestral`.`paquete` (`IDPAQ`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bimestral`.`repartidor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bimestral`.`repartidor` (
  `CEDULA` VARCHAR(255) NOT NULL,
  `ZONA` INT NULL DEFAULT NULL,
  PRIMARY KEY (`CEDULA`),
  CONSTRAINT `FK_REPARTIDOR_CEDULA`
    FOREIGN KEY (`CEDULA`)
    REFERENCES `bimestral`.`persona` (`CEDULA`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
