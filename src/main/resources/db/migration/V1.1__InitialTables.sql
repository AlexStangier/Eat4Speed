create table Adressen
(
    Adress_ID    int auto_increment
        primary key,
    Strasse      varchar(50) not null,
    Hausnummer   int         not null,
    Ort          varchar(50) not null,
    Postleitzahl int         not null
);

create table Allergene
(
    Name         varchar(20)  not null
        primary key,
    Beschreibung varchar(200) null
);

create table Benutzer
(
    Benutzer_ID    int auto_increment
        primary key,
    EmailAdresse   varchar(50) not null,
    Passwort       varchar(50) not null,
    Rolle          varchar(10) not null,
    Paypal_Account varchar(50) not null,
    Nachname       varchar(50) not null,
    Telefonnummer  int         null,
    Benutzername   varchar(50) null,
    Vorname        varchar(50) null
);

create table Bestellhistorie
(
    Bestellhistorien_ID int auto_increment
        primary key
);

create table Blacklist
(
    Eintrag_ID        int auto_increment
        primary key,
    EmailAdresse      varchar(50)  not null,
    Loeschbegruendung varchar(200) not null
);

create table Fahrzeug
(
    Fahrzeug_ID         int auto_increment
        primary key,
    Fahrzeugtyp         varchar(20) not null,
    Kapazitaet_Gerichte int         null,
    Modell              varchar(50) null
);

create table Fahrer
(
    Fahrernummer               int auto_increment
        primary key,
    Benutzer_ID                int         not null,
    Geleistete_Fahrten         int         null,
    Ist_in_Pause               tinyint(1)  not null,
    Fahrzeug                   int         null,
    Geburtsdatum               date        not null,
    Anzahl_Aktueller_Auftraege int         null,
    Aktueller_Standort         int         null,
    verifiziert                tinyint(1)  null,
    Fuehrerschein              varchar(50) null,
    Anrede                     varchar(50) null,
    constraint fk_Fahrer_Aktueller_Standort
        foreign key (Aktueller_Standort) references Adressen (Adress_ID),
    constraint fk_Fahrer_Fahrzeug_R
        foreign key (Fahrzeug) references Fahrzeug (Fahrzeug_ID)
);

create index fk_Fahrer_Fahrzeug
    on Fahrer (Fahrzeug);

create table Kategorie
(
    Name         varchar(20)  not null
        primary key,
    Beschreibung varchar(200) null
);

create table Kunde
(
    Kundennummer    int auto_increment
        primary key,
    Benutzer_ID     int         not null,
    Name            varchar(50) not null,
    Vorname         varchar(50) not null,
    Anschrift       int         not null,
    Bestellhistorie varchar(50) null,
    Anrede          varchar(50) null,
    constraint fk_Kunde_Adresse
        foreign key (Anschrift) references Adressen (Adress_ID),
    constraint fk_Kunde_Benutzername
        foreign key (Benutzer_ID) references Benutzer (Benutzer_ID)
);

create table Oeffnungszeiten
(
    Oeffnungszeiten_ID int auto_increment
        primary key,
    Anfang             time        not null,
    Ende               time        not null,
    Wochentag          varchar(10) not null
);

create table Rechnung
(
    Rechnungs_ID          int auto_increment
        primary key,
    Betrag                double     not null,
    Rechnungsdatum        datetime   not null,
    Zahlungseingang       tinyint(1) not null,
    Datum_Zahlungseingang datetime   null
);

create table Restaurant
(
    Restaurant_ID           int auto_increment
        primary key,
    Benutzer_ID             int          not null,
    Name_des_Restaurants    varchar(50)  not null,
    Allgemeine_Beschreibung varchar(200) not null,
    Anschrift               int          not null,
    Mindestbestellwert      double       null,
    Bestellradius           double       null,
    verifiziert             tinyint(1)   null,
    constraint fk_Restaurant_Anschrift
        foreign key (Anschrift) references Adressen (Adress_ID),
    constraint fk_Restaurant_Benutzer_ID
        foreign key (Benutzer_ID) references Benutzer (Benutzer_ID)
);

create table Benachrichtigung_Fahrer
(
    Benachrichtigungs_ID int auto_increment
        primary key,
    Fahrernummer         int          not null,
    Benachrichtigung     varchar(200) not null,
    Restaurant_ID        int          null,
    Timestamp            datetime     not null,
    constraint fk_Benachrichtigung_Fahrer_Fahrernummer
        foreign key (Fahrernummer) references Fahrer (Fahrernummer),
    constraint fk_Benachrichtigung_Fahrer_Restaurant_ID
        foreign key (Restaurant_ID) references Restaurant (Restaurant_ID)
);

create table Bewertung
(
    Bewertung_ID   int auto_increment
        primary key,
    Kundennummer   int          not null,
    Restaurant_ID  int          not null,
    Sterne         int          not null,
    Text           varchar(200) not null,
    Datum          date         not null,
    wurde_gemeldet tinyint(1)   null,
    constraint fk_Bewertung_Kundennummer
        foreign key (Kundennummer) references Kunde (Kundennummer),
    constraint fk_Bewertung_Restaurant_ID
        foreign key (Restaurant_ID) references Restaurant (Restaurant_ID)
);

create table Einnahmen
(
    Einnahmen_ID  int auto_increment
        primary key,
    Restaurant_ID int           not null,
    Umsatz        double(50, 4) not null,
    Monat         int           not null,
    Jahr          int           not null,
    constraint fk_Einnahmen_Restaurant_ID
        foreign key (Restaurant_ID) references Restaurant (Restaurant_ID)
);

create table Favoritenliste_Restaurants
(
    Restaurant_ID       int  not null,
    Kundennummer        int  not null,
    Hinzufuegedatum     date not null,
    Anzahl_Bestellungen int  null,
    primary key (Restaurant_ID, Kundennummer),
    constraint fk_Favoritenliste_Restaurants_Kundennummer
        foreign key (Kundennummer) references Kunde (Kundennummer),
    constraint fk_Favoritenliste_Restaurants_Restaurant_ID
        foreign key (Restaurant_ID) references Restaurant (Restaurant_ID)
);

create table Gericht
(
    Gericht_ID    int auto_increment
        primary key,
    Restaurant_ID int          not null,
    Name          varchar(50)  not null,
    Beschreibung  varchar(200) not null,
    Abbildung     varchar(200) null,
    Preis         double       not null,
    Verfuegbar    tinyint(1)   not null,
    Ist_Getraenk  tinyint(1)   not null,
    constraint fk_Gericht_Restaurant_ID
        foreign key (Restaurant_ID) references Restaurant (Restaurant_ID)
);

create table Benachrichtigung_Kunde
(
    Benachrichtigungs_ID int auto_increment
        primary key,
    Kunde_ID             int          not null,
    Benachrichtigung     varchar(200) not null,
    Gericht              int          null,
    Timestamp            datetime     not null,
    constraint fk_Benachrichtigung_Kunde_Gericht
        foreign key (Gericht) references Gericht (Gericht_ID),
    constraint fk_Benachrichtigung_Kunde_Kunde_ID
        foreign key (Kunde_ID) references Kunde (Kundennummer)
);

create table Favoritenliste_Gerichte
(
    Gericht_ID          int  not null,
    Kundennummer        int  not null,
    Hinzufuegedatum     date not null,
    Anzahl_Bestellungen int  null,
    primary key (Gericht_ID, Kundennummer),
    constraint fk_Favoritenliste_Gerichte_Gericht_ID
        foreign key (Gericht_ID) references Gericht (Gericht_ID),
    constraint fk_Favoritenliste_Gerichte_Kundennummer
        foreign key (Kundennummer) references Kunde (Kundennummer)
);

create table Gericht_Allergene
(
    Gericht_ID int         not null,
    Allergen   varchar(50) not null,
    primary key (Gericht_ID, Allergen),
    constraint fk_Allergene_Gericht_Allergene
        foreign key (Allergen) references Allergene (Name),
    constraint fk_Gericht_Gericht_Allergene
        foreign key (Gericht_ID) references Gericht (Gericht_ID)
);

create table Gericht_Kategorie
(
    Gericht_ID int         not null,
    Kategorie  varchar(20) not null,
    primary key (Gericht_ID, Kategorie),
    constraint fk_Gericht_Gericht_Kategorie
        foreign key (Gericht_ID) references Gericht (Gericht_ID),
    constraint fk_Kategorie_Gericht_Kategorie
        foreign key (Kategorie) references Kategorie (Name)
);

create table Restaurant_Zeiten
(
    Restaurant_ID      int not null,
    Oeffnungszeiten_ID int not null,
    primary key (Restaurant_ID, Oeffnungszeiten_ID),
    constraint fk_Restaurant_Zeiten_Oeffnungszeiten_ID
        foreign key (Oeffnungszeiten_ID) references Oeffnungszeiten (Oeffnungszeiten_ID),
    constraint fk_Restaurant_Zeiten_Restaurant_ID
        foreign key (Restaurant_ID) references Restaurant (Restaurant_ID)
);

create table Schicht
(
    Schicht_ID   int auto_increment
        primary key,
    Fahrernummer int      not null,
    Anfang       datetime not null,
    Ende         datetime not null,
    constraint fk_Schicht_Fahrernummer
        foreign key (Fahrernummer) references Fahrer (Fahrernummer)
);

create table Status
(
    Status_Name  varchar(10) not null
        primary key,
    Rechnungs_ID int         not null,
    constraint fk_Status_Rechnungs_ID
        foreign key (Rechnungs_ID) references Rechnung (Rechnungs_ID)
);

create table Auftrag
(
    Auftrags_ID                           int auto_increment
        primary key,
    Kundennummer                          int           not null,
    Auftragnehmer                         int           not null,
    Timestamp                             datetime      not null,
    Anschrift                             int           not null,
    Status                                varchar(10)   not null,
    Lieferdistanz                         double(50, 4) null,
    Timestamp_On_Customer_Demand          datetime      null,
    Geschaetzte_Fahrtzeit_Restaurant_Ziel double        null,
    constraint fk_Auftrag_Auftragnehmer
        foreign key (Auftragnehmer) references Restaurant (Restaurant_ID),
    constraint fk_Auftrag_Kundennummer
        foreign key (Kundennummer) references Kunde (Kundennummer),
    constraint fk_Auftrag_Status
        foreign key (Status) references Status (Status_Name)
);

create table Bestellung
(
    Bestell_ID          int auto_increment
        primary key,
    Bestellhistorien_ID int      not null,
    Auftrags_ID         int      null,
    Timestamp           datetime not null,
    Rechnung            int      null,
    constraint Bestellung_Bestellhistorien_ID
        foreign key (Bestellhistorien_ID) references Bestellhistorie (Bestellhistorien_ID),
    constraint Bestellung_Rechnung
        foreign key (Rechnung) references Rechnung (Rechnungs_ID),
    constraint fk_Bestellung_Auftrags_ID
        foreign key (Auftrags_ID) references Auftrag (Auftrags_ID)
);

create table Bestellzuordnung
(
    Bestell_ID int not null,
    Gericht_ID int not null,
    primary key (Bestell_ID, Gericht_ID),
    constraint fk_Bestellzuordnung_Bestell_ID
        foreign key (Bestell_ID) references Bestellung (Bestell_ID),
    constraint fk_Bestellzuordnung_Gericht_ID
        foreign key (Gericht_ID) references Gericht (Gericht_ID)
);

create table Fahrtenplan_Station
(
    Stations_ID                  int auto_increment
        primary key,
    Auftrag                      int    not null,
    Fahrer                       int    not null,
    Liefer_Abholadresse          int    null,
    Vorherige_Station            int    null,
    Naechste_Station             int    null,
    Fahrzeit_A_B                 double null,
    Distanz_zu_naechster_Station double null,
    Geschaetzte_Fahrtzeit        double null,
    constraint fk_Fahrtenplan_Station_Auftrag
        foreign key (Auftrag) references Auftrag (Auftrags_ID),
    constraint fk_Fahrtenplan_Station_Fahrer
        foreign key (Fahrer) references Fahrer (Fahrernummer),
    constraint fk_Fahrtenplan_Station_Liefer_Abholadresse
        foreign key (Liefer_Abholadresse) references Adressen (Adress_ID),
    constraint fk_Fahrtenplan_Station_Naechste_Station
        foreign key (Naechste_Station) references Fahrtenplan_Station (Stations_ID),
    constraint fk_Fahrtenplan_Station_Vorherige_Station
        foreign key (Vorherige_Station) references Fahrtenplan_Station (Stations_ID)
);

create table Urlaub
(
    Urlaubs_ID   int auto_increment
        primary key,
    Fahrernummer int  not null,
    Anfang       date not null,
    Ende         date not null,
    constraint fk_Urlaub_Fahrernummer
        foreign key (Fahrernummer) references Fahrer (Fahrernummer)
);

create table hibernate_sequence
(
    ORDID int auto_increment
        primary key
);

