-- MySQL Script generated by MySQL Workbench
-- Sat Jan 30 18:54:35 2021
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema eatforspeed
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema eatforspeed
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `eatforspeed` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `eatforspeed`;


-- -----------------------------------------------------
-- Table `eatforspeed`.`hibernate_sequence`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `eatforspeed`.`hibernate_sequence`
(
    ORDID INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (ordid)
)
    AUTO_INCREMENT = 1;

-- -----------------------------------------------------
-- Table `eatforspeed`.`Adressen`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Adressen`
(
    `Adress_ID`    INT         NOT NULL AUTO_INCREMENT,
    `Strasse`      VARCHAR(50) NOT NULL,
    `Hausnummer`   INT         NOT NULL,
    `Ort`          VARCHAR(50) NOT NULL,
    `Postleitzahl` INT         NOT NULL,
    PRIMARY KEY (`Adress_ID`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 4
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eatforspeed`.`Allergene`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Allergene`
(
    `Name`         VARCHAR(20)  NOT NULL,
    `Beschreibung` VARCHAR(200) NULL DEFAULT NULL,
    PRIMARY KEY (`Name`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE `eatforspeed`.`Kunde`
     DROP CONSTRAINT `fk_Kunde_Benutzername`;

ALTER TABLE `eatforspeed`.`Restaurant`
    DROP CONSTRAINT `fk_Restaurant_Benutzername`;

ALTER TABLE `eatforspeed`.`Fahrer`
    DROP CONSTRAINT `fk_Fahrer_Benutzer`;

-- -----------------------------------------------------
-- Table `eatforspeed`.`Benutzer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Benutzer`
(
    `Benutzer_ID`     VARCHAR(50) NOT NULL,
    `Vorname`         VARCHAR(50) NOT NULL,
    `Nachname`        VARCHAR(50) NOT NULL,
    `E_Mail_Addresse` VARCHAR(50) NOT NULL,
    `Passwort`        VARCHAR(50) NOT NULL,
    `Rolle`           VARCHAR(50) NOT NULL,
    `Paypal_Account`  VARCHAR(50) NOT NULL,
    `Telefonnummer`   INT,
    PRIMARY KEY (`Benutzer_ID`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE `eatforspeed`.`Benutzer`
    DROP PRIMARY KEY,
    ADD COLUMN `Nachname`        VARCHAR(50) NOT NULL,
    ADD COLUMN `Telefonnummer`   INT,
    CHANGE `Benutzername` `Benutzer_ID` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    CHANGE `E_Mail_Addresse` `EmailAdresse` VARCHAR(50) NOT NULL,
    ADD COLUMN `Benutzername`        VARCHAR(50),
    ADD COLUMN `Vorname`        VARCHAR(50);

-- -----------------------------------------------------
-- Table `eatforspeed`.`Kunde`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Kunde`
(
    `Kundennummer`    INT         NOT NULL AUTO_INCREMENT,
    `Anrede`          VARCHAR(50),
    `Benutzer_ID`     VARCHAR(50) NOT NULL,
    `Name`            VARCHAR(50) NOT NULL,
    `Vorname`         VARCHAR(50) NOT NULL,
    `Anschrift`       INT         NOT NULL,
    `Bestellhistorie` VARCHAR(50) NULL DEFAULT NULL,
    PRIMARY KEY (`Kundennummer`),
    INDEX `fk_Kunde_Benutzername` (`Benutzer_ID` ASC),
    INDEX `fk_Kunde_Adresse` (`Anschrift` ASC),
    CONSTRAINT `fk_Kunde_Adresse`
        FOREIGN KEY (`Anschrift`)
            REFERENCES `eatforspeed`.`Adressen` (`Adress_ID`),
    CONSTRAINT `fk_Kunde_Benutzername`
        FOREIGN KEY (`Benutzer_ID`)
            REFERENCES `eatforspeed`.`Benutzer` (`Benutzer_ID`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 6
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE `eatforspeed`.`Kunde`
    CHANGE `Benutzername` `Benutzer_ID`     INT NOT NULL,
    DROP INDEX `fk_Kunde_Benutzername`,
    ADD CONSTRAINT `fk_Kunde_Benutzername`
        FOREIGN KEY (`Benutzer_ID`)
            REFERENCES `eatforspeed`.`Benutzer` (`Benutzer_ID`),
    ADD COLUMN `Anrede`          VARCHAR(50);



-- -----------------------------------------------------
-- Table `eatforspeed`.`Rechnung`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Rechnung`
(
    `Rechnungs_ID`          INT        NOT NULL AUTO_INCREMENT,
    `Betrag`                DOUBLE     NOT NULL,
    `Rechnungsdatum`        DATETIME   NOT NULL,
    `Zahlungseingang`       TINYINT(1) NOT NULL,
    `Datum_Zahlungseingang` DATETIME   NULL DEFAULT NULL,
    PRIMARY KEY (`Rechnungs_ID`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eatforspeed`.`Status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Status`
(
    `Status_Name`  VARCHAR(10) NOT NULL,
    `Rechnungs_ID` INT         NOT NULL,
    PRIMARY KEY (`Status_Name`),
    INDEX `fk_Status_Rechnungs_ID` (`Rechnungs_ID` ASC),
    CONSTRAINT `fk_Status_Rechnungs_ID`
        FOREIGN KEY (`Rechnungs_ID`)
            REFERENCES `eatforspeed`.`Rechnung` (`Rechnungs_ID`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eatforspeed`.`Auftrag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Auftrag`
(
    `Auftrags_ID`   INT           NOT NULL AUTO_INCREMENT,
    `Kundennummer`  INT           NOT NULL,
    `Auftragnehmer` INT           NOT NULL,
    `Timestamp_On_Customer_Demand` DATETIME,
    `Timestamp`     DATETIME      NOT NULL,
    `Anschrift`     INT           NOT NULL,
    `Status`        VARCHAR(10)   NOT NULL,
    `Lieferdistanz` DOUBLE        NULL DEFAULT NULL,
    `Geschaetzte_Fahrtzeit_Restaurant_Ziel` DOUBLE(50,2),
    PRIMARY KEY (`Auftrags_ID`),
    INDEX `fk_Auftrag_Kundennummer` (`Kundennummer` ASC),
    INDEX `fk_Auftrag_Status` (`Status` ASC),
    CONSTRAINT `fk_Auftrag_Kundennummer`
        FOREIGN KEY (`Kundennummer`)
            REFERENCES `eatforspeed`.`Kunde` (`Kundennummer`),
    CONSTRAINT `fk_Auftrag_Status`
        FOREIGN KEY (`Status`)
            REFERENCES `eatforspeed`.`Status` (`Status_Name`),
    CONSTRAINT `fk_Auftrag_Auftragnehmer`
        FOREIGN KEY (`Auftragnehmer`)
            REFERENCES `eatforspeed`.`Restaurant` (`Restaurant_ID`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE `eatforspeed`.`Auftrag`
    ADD CONSTRAINT `fk_Auftrag_Auftragnehmer`
        FOREIGN KEY (`Auftragnehmer`)
            REFERENCES `eatforspeed`.`Restaurant` (`Restaurant_ID`),
    ADD COLUMN `Timestamp_On_Customer_Demand` DATETIME,
    ADD COLUMN `Geschaetzte_Fahrtzeit_Restaurant_Ziel` DOUBLE;

-- -----------------------------------------------------
-- Table `eatforspeed`.`Fahrzeug`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Fahrzeug`
(
    `Fahrzeug_ID` INT         NOT NULL AUTO_INCREMENT,
    `Fahrzeugtyp` VARCHAR(20) NOT NULL,
    `Kapazitaet_Gerichte` INT,
    `Modell`      VARCHAR(50),
    PRIMARY KEY (`Fahrzeug_ID`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE `eatforspeed`.`Fahrzeug`
    ADD COLUMN `Kapazitaet_Gerichte` INT,
    ADD COLUMN `Modell`      VARCHAR(50);

-- -----------------------------------------------------
-- Table `eatforspeed`.`Fahrer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Fahrer`
(
    `Fahrernummer`       INT         NOT NULL AUTO_INCREMENT,
    `Benutzer_ID`       VARCHAR(50) NOT NULL,
    `Geleistete_Fahrten` INT         NULL DEFAULT NULL,
    `Ist_in_Pause`       TINYINT(1)  NOT NULL,
    `Geburtsdatum`       DATE        NOT NULL,
    `Fahrzeug`           INT         NOT NULL,
    `Anzahl_Aktueller_Auftraege` INT,
    `Aktueller_Standort` INT,
    `verifiziert`        TINYINT(1),
    `Fuehrerschein`      VARCHAR(50),
    `Anrede`             VARCHAR(50),
    PRIMARY KEY (`Fahrernummer`),
    INDEX `fk_Fahrer_Fahrzeug` (`Fahrzeug` ASC),
    INDEX `fk_Fahrer_Benutzer` (`Benutzer_ID` ASC),
    CONSTRAINT `fk_Fahrer_Benutzer`
        FOREIGN KEY (`Benutzer_ID`)
            REFERENCES `eatforspeed`.`Benutzer` (`Benutzer_ID`),
    CONSTRAINT `fk_Fahrer_Fahrzeug`
        FOREIGN KEY (`Fahrzeug`)
            REFERENCES `eatforspeed`.`Fahrzeug` (`Fahrzeug_ID`),
    CONSTRAINT `fk_Fahrer_Aktueller_Standort`
        FOREIGN KEY (`Aktueller_Standort`)
            REFERENCES `eatforspeed`.`Adressen` (`Adress_ID`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE `eatforspeed`.`Fahrer`
    CHANGE `Benutzername` `Benutzer_ID`       INT NOT NULL,
    DROP INDEX `fk_Fahrer_Benutzer`,
    ADD COLUMN `Geburtsdatum`       DATE        NOT NULL,
    ADD COLUMN `Anzahl_Aktueller_Auftraege` INT,
    ADD COLUMN `Aktueller_Standort` INT,
    ADD COLUMN `verifiziert`        TINYINT(1),
    ADD COLUMN `Fuehrerschein`      VARCHAR(50),
    ADD COLUMN `Anrede`             VARCHAR(50),
    ADD CONSTRAINT `fk_Fahrer_Aktueller_Standort`
    FOREIGN KEY (`Aktueller_Standort`)
    REFERENCES `eatforspeed`.`Adressen` (`Adress_ID`);

-- -----------------------------------------------------
-- Table `eatforspeed`.`Restaurant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Restaurant`
(
    `Restaurant_ID`           INT          NOT NULL AUTO_INCREMENT,
    `Benutzer_ID`            VARCHAR(50)  NOT NULL,
    `Name_des_Restaurants`    VARCHAR(50)  NOT NULL,
    `Allgemeine_Beschreibung` VARCHAR(200) NOT NULL,
    `Anschrift`               INT          NOT NULL,
    `Mindestbestellwert`      DOUBLE,
    `Bestellradius`           DOUBLE,
    `verifiziert`             TINYINT(1),
    PRIMARY KEY (`Restaurant_ID`),
    INDEX `fk_Restaurant_Benutzername` (`Benutzer_ID` ASC),
    INDEX `fk_Restaurant_Anschrift` (`Anschrift` ASC),
    CONSTRAINT `fk_Restaurant_Anschrift`
        FOREIGN KEY (`Anschrift`)
            REFERENCES `eatforspeed`.`Adressen` (`Adress_ID`),
    CONSTRAINT `fk_Restaurant_Benutzername`
        FOREIGN KEY (`Benutzer_ID`)
            REFERENCES `eatforspeed`.`Benutzer` (`Benutzer_ID`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE `eatforspeed`.`Restaurant`
    DROP INDEX `fk_Restaurant_Benutzername`,
    CHANGE `Benutzername` `Benutzer_ID`            INT  NOT NULL,
    ADD COLUMN `Mindestbestellwert`      DOUBLE,
    ADD COLUMN `Bestellradius`           DOUBLE,
    ADD COLUMN `verifiziert`             TINYINT(1),
    ADD CONSTRAINT `fk_Restaurant_Benutzer_ID`
        FOREIGN KEY (`Benutzer_ID`)
            REFERENCES `eatforspeed`.`Benutzer` (`Benutzer_ID`);

-- -----------------------------------------------------
-- Table `eatforspeed`.`Benachrichtigung_Fahrer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Benachrichtigung_Fahrer`
(
    `Benachrichtigungs_ID` INT          NOT NULL AUTO_INCREMENT,
    `Fahrernummer`         INT          NOT NULL,
    `Benachrichtigung`     VARCHAR(200) NOT NULL,
    `Restaurant_ID`        INT          NULL DEFAULT NULL,
    `Timestamp`            DATETIME     NOT NULL,
    PRIMARY KEY (`Benachrichtigungs_ID`),
    INDEX `fk_Benachrichtigung_Fahrer_Fahrernummer` (`Fahrernummer` ASC),
    INDEX `fk_Benachrichtigung_Fahrer_Restaurant_ID` (`Restaurant_ID` ASC),
    CONSTRAINT `fk_Benachrichtigung_Fahrer_Fahrernummer`
        FOREIGN KEY (`Fahrernummer`)
            REFERENCES `eatforspeed`.`Fahrer` (`Fahrernummer`),
    CONSTRAINT `fk_Benachrichtigung_Fahrer_Restaurant_ID`
        FOREIGN KEY (`Restaurant_ID`)
            REFERENCES `eatforspeed`.`Restaurant` (`Restaurant_ID`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eatforspeed`.`Gericht`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Gericht`
(
    `Gericht_ID`    INT          NOT NULL AUTO_INCREMENT,
    `Restaurant_ID` INT          NOT NULL,
    `Name`          VARCHAR(50)  NOT NULL,
    `Beschreibung`  VARCHAR(200) NOT NULL,
    `Abbildung`     VARCHAR(200) NULL DEFAULT NULL,
    `Preis`         DOUBLE       NOT NULL,
    `Verfuegbar`    TINYINT(1)   NOT NULL,
    PRIMARY KEY (`Gericht_ID`),
    INDEX `fk_Gericht_Restaurant_ID` (`Restaurant_ID` ASC),
    CONSTRAINT `fk_Gericht_Restaurant_ID`
        FOREIGN KEY (`Restaurant_ID`)
            REFERENCES `eatforspeed`.`Restaurant` (`Restaurant_ID`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE `eatforspeed`.`Gericht`
    CHANGE `Abbildung` `Abbildung`     VARCHAR(200) NULL DEFAULT NULL;

-- -----------------------------------------------------
-- Table `eatforspeed`.`Benachrichtigung_Kunde`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Benachrichtigung_Kunde`
(
    `Benachrichtigungs_ID` INT          NOT NULL AUTO_INCREMENT,
    `Kunde_ID`             INT          NOT NULL,
    `Benachrichtigung`     VARCHAR(200) NOT NULL,
    `Gericht`              INT          NULL DEFAULT NULL,
    `Timestamp`            DATETIME     NOT NULL,
    PRIMARY KEY (`Benachrichtigungs_ID`),
    INDEX `fk_Benachrichtigung_Kunde_Kunde_ID` (`Kunde_ID` ASC),
    INDEX `fk_Benachrichtigung_Kunde_Gericht` (`Gericht` ASC),
    CONSTRAINT `fk_Benachrichtigung_Kunde_Gericht`
        FOREIGN KEY (`Gericht`)
            REFERENCES `eatforspeed`.`Gericht` (`Gericht_ID`),
    CONSTRAINT `fk_Benachrichtigung_Kunde_Kunde_ID`
        FOREIGN KEY (`Kunde_ID`)
            REFERENCES `eatforspeed`.`Kunde` (`Kundennummer`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eatforspeed`.`Bestellhistorie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Bestellhistorie`
(
    `Bestellhistorien_ID` INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`Bestellhistorien_ID`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE `eatforspeed`.`Bestellhistorie`
    DROP COLUMN `Restaurant`,
    DROP INDEX `fk_Bestellhistorie_Restaurant`,
    DROP CONSTRAINT `fk_Bestellhistorie_Restaurant`;

-- -----------------------------------------------------
-- Table `eatforspeed`.`Bestellung`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Bestellung`
(
    `Bestell_ID`          INT      NOT NULL AUTO_INCREMENT,
    `Bestellhistorien_ID` INT      NOT NULL,
    `Auftrags_ID`         INT      NULL DEFAULT NULL,
    `Timestamp`           DATETIME NOT NULL,
    `Rechnung`            INT      NULL DEFAULT NULL,
    PRIMARY KEY (`Bestell_ID`),
    INDEX `fk_Bestellung_Auftrags_ID` (`Auftrags_ID` ASC),
    CONSTRAINT `fk_Bestellung_Auftrags_ID`
        FOREIGN KEY (`Auftrags_ID`)
            REFERENCES `eatforspeed`.`Auftrag` (`Auftrags_ID`),
    CONSTRAINT `fk_Bestellung_Rechnung`
        FOREIGN KEY (`Rechnung`)
            REFERENCES `eatforspeed`.`Rechnung` (`Rechnungs_ID`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE `eatforspeed`.`Bestellung`
    ADD CONSTRAINT `Bestellung_Bestellhistorien_ID`
        FOREIGN KEY (`Bestellhistorien_ID`)
            REFERENCES `eatforspeed`.`Bestellhistorie` (`Bestellhistorien_ID`),
    ADD CONSTRAINT `Bestellung_Rechnung`
        FOREIGN KEY (`Rechnung`)
            REFERENCES `eatforspeed`.`Rechnung` (`Rechnungs_ID`);
-- -----------------------------------------------------
-- Table `eatforspeed`.`Bestellzuordnung`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Bestellzuordnung`
(
    `Bestell_ID` INT NOT NULL,
    `Gericht_ID` INT NOT NULL,
    PRIMARY KEY (`Bestell_ID`, `Gericht_ID`),
    INDEX `fk_Bestellzuordnung_Gericht_ID` (`Gericht_ID` ASC),
    CONSTRAINT `fk_Bestellzuordnung_Bestell_ID`
        FOREIGN KEY (`Bestell_ID`)
            REFERENCES `eatforspeed`.`Bestellung` (`Bestell_ID`),
    CONSTRAINT `fk_Bestellzuordnung_Gericht_ID`
        FOREIGN KEY (`Gericht_ID`)
            REFERENCES `eatforspeed`.`Gericht` (`Gericht_ID`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eatforspeed`.`Einnahmen`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Einnahmen`
(
    `Einnahmen_ID`  INT           NOT NULL AUTO_INCREMENT,
    `Restaurant_ID` INT           NOT NULL,
    `Umsatz`        DOUBLE(50, 4) NOT NULL,
    `Monat`         INT           NOT NULL,
    `Jahr`          INT           NOT NULL,
    PRIMARY KEY (`Einnahmen_ID`),
    CONSTRAINT `fk_Einnahmen_Restaurant_ID`
        FOREIGN KEY (`Restaurant_ID`)
            REFERENCES `eatforspeed`.`Restaurant` (`Restaurant_ID`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE `eatforspeed`.`Einnahmen`
    ADD CONSTRAINT `fk_Einnahmen_Restaurant_ID`
        FOREIGN KEY (`Restaurant_ID`)
            REFERENCES `eatforspeed`.`Restaurant` (`Restaurant_ID`);

-- -----------------------------------------------------
-- Table `eatforspeed`.`Gericht_Allergene`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Gericht_Allergene`
(
    `Gericht_ID` INT         NOT NULL,
    `Allergen`   VARCHAR(50) NOT NULL,
    PRIMARY KEY (`Gericht_ID`, `Allergen`),
    INDEX `fk_Allergene_Gericht_Allergene` (`Allergen` ASC),
    CONSTRAINT `fk_Allergene_Gericht_Allergene`
        FOREIGN KEY (`Allergen`)
            REFERENCES `eatforspeed`.`Allergene` (`Name`),
    CONSTRAINT `fk_Gericht_Gericht_Allergene`
        FOREIGN KEY (`Gericht_ID`)
            REFERENCES `eatforspeed`.`Gericht` (`Gericht_ID`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE `eatforspeed`.`Gericht_Kategorie`
    DROP CONSTRAINT `fk_Kategorie_Gericht_Kategorie`;

-- -----------------------------------------------------
-- Table `eatforspeed`.`Kategorie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Kategorie`
(
    `Name`         VARCHAR(20)  NOT NULL,
    `Beschreibung` VARCHAR(200) NULL DEFAULT NULL,
    PRIMARY KEY (`Name`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE `eatforspeed`.`Kategorie`
    DROP PRIMARY KEY,
    DROP COLUMN `Name`,
    CHANGE `Kategorie_ID` `Name`         VARCHAR(20)  NOT NULL PRIMARY KEY;

-- -----------------------------------------------------
-- Table `eatforspeed`.`Gericht_Kategorie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Gericht_Kategorie`
(
    `Gericht_ID`   INT NOT NULL,
    `Kategorie` INT NOT NULL,
    PRIMARY KEY (`Gericht_ID`, `Kategorie`),
    CONSTRAINT `fk_Gericht_Gericht_Kategorie`
        FOREIGN KEY (`Gericht_ID`)
            REFERENCES `eatforspeed`.`Gericht` (`Gericht_ID`),
    CONSTRAINT `fk_Kategorie_Gericht_Kategorie`
        FOREIGN KEY (`Kategorie`)
            REFERENCES `eatforspeed`.`Kategorie` (`Name`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE `eatforspeed`.`Gericht_Kategorie`
    DROP PRIMARY KEY,
    CHANGE `Kategorie_ID` `Kategorie` VARCHAR(20) NOT NULL,
    ADD CONSTRAINT `fk_Kategorie_Gericht_Kategorie`
        FOREIGN KEY (`Kategorie`)
            REFERENCES `eatforspeed`.`Kategorie` (`Name`),
    ADD PRIMARY KEY (`Gericht_ID`, `Kategorie`);

-- -----------------------------------------------------
-- Table `eatforspeed`.`Oeffnungszeiten`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Oeffnungszeiten`
(
    `Oeffnungszeiten_ID` INT         NOT NULL AUTO_INCREMENT,
    `Anfang`             TIME        NOT NULL,
    `Ende`               TIME        NOT NULL,
    `Wochentag`          VARCHAR(10) NOT NULL,
    PRIMARY KEY (`Oeffnungszeiten_ID`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `eatforspeed`.`Restaurant_Zeiten`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Restaurant_Zeiten`
(
    `Restaurant_ID`      INT NOT NULL,
    `Oeffnungszeiten_ID` INT NOT NULL,
    PRIMARY KEY (`Restaurant_ID`),
    INDEX `fk_Restaurant_Zeiten_Oeffnungszeiten_ID` (`Oeffnungszeiten_ID` ASC),
    CONSTRAINT `fk_Restaurant_Zeiten_Oeffnungszeiten_ID`
        FOREIGN KEY (`Oeffnungszeiten_ID`)
            REFERENCES `eatforspeed`.`Oeffnungszeiten` (`Oeffnungszeiten_ID`),
    CONSTRAINT `fk_Restaurant_Zeiten_Restaurant_ID`
        FOREIGN KEY (`Restaurant_ID`)
            REFERENCES `eatforspeed`.`Restaurant` (`Restaurant_ID`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE `eatforspeed`.`Restaurant_Zeiten`
    DROP PRIMARY KEY,
    ADD PRIMARY KEY (`Restaurant_ID`, `Oeffnungszeiten_ID`);

-- -----------------------------------------------------
-- Table `eatforspeed`.`Urlaub`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Urlaub`
(
    `Urlaubs_ID`   INT  NOT NULL AUTO_INCREMENT,
    `Fahrernummer` INT  NOT NULL,
    `Anfang`       DATE NOT NULL,
    `Ende`         DATE NOT NULL,
    PRIMARY KEY (`Urlaubs_ID`),
    INDEX `fk_Urlaub_Fahrernummer` (`Fahrernummer` ASC),
    CONSTRAINT `fk_Urlaub_Fahrernummer`
        FOREIGN KEY (`Fahrernummer`)
            REFERENCES `eatforspeed`.`Fahrer` (`Fahrernummer`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `eatforspeed`.`Schicht`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Schicht`
(
    `Schicht_ID`   INT  NOT NULL AUTO_INCREMENT,
    `Fahrernummer` INT  NOT NULL,
    `Anfang`       DATETIME NOT NULL,
    `Ende`         DATETIME NOT NULL,
    PRIMARY KEY (`Schicht_ID`),
    CONSTRAINT `fk_Schicht_Fahrernummer`
        FOREIGN KEY (`Fahrernummer`)
            REFERENCES `eatforspeed`.`Fahrer` (`Fahrernummer`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `eatforspeed`.`Blacklist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Blacklist`
(
    `Eintrag_ID`   INT  NOT NULL AUTO_INCREMENT,
    `EmailAdresse` VARCHAR(50)  NOT NULL,
    `Loeschbegruendung`  VARCHAR(200) NOT NULL,
    PRIMARY KEY (`Eintrag_ID`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `eatforspeed`.`Bewertung`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Bewertung`
(
    `Bewertung_ID`    INT  NOT NULL AUTO_INCREMENT,
    `Kundennummer`    INT  NOT NULL,
    `Restaurant_ID`   INT  NOT NULL,
    `Sterne`          INT NOT NULL,
    `Text`            VARCHAR(200) NOT NULL,
    `Datum`           DATE NOT NULL,
    `wurde_gemeldet`  TINYINT(1),
    PRIMARY KEY (`Bewertung_ID`),
    CONSTRAINT `fk_Bewertung_Kundennummer`
        FOREIGN KEY (`Kundennummer`)
            REFERENCES `eatforspeed`.`Kunde` (`Kundennummer`),
    CONSTRAINT `fk_Bewertung_Restaurant_ID`
        FOREIGN KEY (`Restaurant_ID`)
            REFERENCES `eatforspeed`.`Restaurant` (`Restaurant_ID`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `eatforspeed`.`Favoritenliste_Restaurants`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Favoritenliste_Restaurants`
(
    `Restaurant_ID`    INT  NOT NULL,
    `Kundennummer`     INT  NOT NULL,
    `Hinzufuegedatum`  DATE NOT NULL,
    `Anzahl_Bestellungen`   INT,
    PRIMARY KEY (`Restaurant_ID`,`Kundennummer`),
    CONSTRAINT `fk_Favoritenliste_Restaurants_Kundennummer`
        FOREIGN KEY (`Kundennummer`)
            REFERENCES `eatforspeed`.`Kunde` (`Kundennummer`),
    CONSTRAINT `fk_Favoritenliste_Restaurants_Restaurant_ID`
        FOREIGN KEY (`Restaurant_ID`)
            REFERENCES `eatforspeed`.`Restaurant` (`Restaurant_ID`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `eatforspeed`.`Favoritenliste_Gerichte`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Favoritenliste_Gerichte`
(
    `Gericht_ID`       INT  NOT NULL,
    `Kundennummer`     INT  NOT NULL,
    `Hinzufuegedatum`  DATE NOT NULL,
    `Anzahl_Bestellungen`   INT,
    PRIMARY KEY (`Gericht_ID`,`Kundennummer`),
    CONSTRAINT `fk_Favoritenliste_Gerichte_Kundennummer`
        FOREIGN KEY (`Kundennummer`)
            REFERENCES `eatforspeed`.`Kunde` (`Kundennummer`),
    CONSTRAINT `fk_Favoritenliste_Gerichte_Gericht_ID`
        FOREIGN KEY (`Gericht_ID`)
            REFERENCES `eatforspeed`.`Gericht` (`Gericht_ID`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `eatforspeed`.`Fahrtenplan_Station`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eatforspeed`.`Fahrtenplan_Station`
(
    `Stations_ID`                   INT  NOT NULL AUTO_INCREMENT,
    `Auftrag`                       INT  NOT NULL,
    `Fahrer`                        INT  NOT NULL,
    `Liefer_Abholadresse`           INT,
    `Vorherige_Station`             INT,
    `Naechste_Station`              INT,
    `Fahrzeit_A_B`                  DOUBLE,
    `Distanz_zu_naechster_Station`  DOUBLE,
    `Geschaetzte_Fahrtzeit`         DOUBLE,
    PRIMARY KEY (`Stations_ID`),
    CONSTRAINT `fk_Fahrtenplan_Station_Auftrag`
        FOREIGN KEY (`Auftrag`)
            REFERENCES `eatforspeed`.`Auftrag` (`Auftrags_ID`),
    CONSTRAINT `fk_Fahrtenplan_Station_Fahrer`
        FOREIGN KEY (`Fahrer`)
            REFERENCES `eatforspeed`.`Fahrer` (`Fahrernummer`),
    CONSTRAINT `fk_Fahrtenplan_Station_Liefer_Abholadresse`
        FOREIGN KEY (`Liefer_Abholadresse`)
            REFERENCES `eatforspeed`.`Adressen` (`Adress_ID`),
    CONSTRAINT `fk_Fahrtenplan_Station_Vorherige_Station`
        FOREIGN KEY (`Vorherige_Station`)
            REFERENCES `eatforspeed`.`Fahrtenplan_Station` (`Stations_ID`),
    CONSTRAINT `fk_Fahrtenplan_Station_Naechste_Station`
        FOREIGN KEY (`Naechste_Station`)
            REFERENCES `eatforspeed`.`Fahrtenplan_Station` (`Stations_ID`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;