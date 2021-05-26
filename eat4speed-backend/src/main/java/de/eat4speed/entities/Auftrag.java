package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Auftrag extends PanacheEntityBase implements Serializable {

    public Auftrag(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long auftrags_ID;
    private int auftragnehmer;
    private Timestamp timestamp;
    private Timestamp timestamp_On_Customer_Demand;
    private int anschrift;
    private Double lieferdistanz;
    private int kundennummer;
    private String status;
    private int geschaetzte_fahrtzeit_restaurant_ziel;

    public int getAuftragnehmer() {
        return auftragnehmer;
    }

    public void setAuftragnehmer(int auftragnehmer) {
        this.auftragnehmer = auftragnehmer;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getAnschrift() {
        return anschrift;
    }

    public void setAnschrift(int anschrift) {
        this.anschrift = anschrift;
    }

    public Double getLieferdistanz() {
        return lieferdistanz;
    }

    public void setLieferdistanz(Double lieferdistanz) {
        this.lieferdistanz = lieferdistanz;
    }

    public long getAuftrags_ID() {
        return auftrags_ID;
    }

    public void setAuftrags_ID(long auftrags_ID) {
        this.auftrags_ID = auftrags_ID;
    }

    public int getKundennummer() {
        return kundennummer;
    }

    public void setKundennummer(int kundennummer) {
        this.kundennummer = kundennummer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getGeschaetzte_fahrtzeit_restaurant_ziel() {
        return geschaetzte_fahrtzeit_restaurant_ziel;
    }

    public void setGeschaetzte_fahrtzeit_restaurant_ziel(int geschaetzte_fahrzeit_restaurant_ziel) {
        this.geschaetzte_fahrtzeit_restaurant_ziel = geschaetzte_fahrzeit_restaurant_ziel;
    }

    public Timestamp getTimestamp_On_Customer_Demand() {
        return timestamp_On_Customer_Demand;
    }

    public void setTimestamp_On_Customer_Demand(Timestamp timestamp_On_Customer_Demand) {
        this.timestamp_On_Customer_Demand = timestamp_On_Customer_Demand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auftrag auftrag = (Auftrag) o;
        return auftrags_ID == auftrag.auftrags_ID && auftragnehmer == auftrag.auftragnehmer && anschrift == auftrag.anschrift && kundennummer == auftrag.kundennummer && geschaetzte_fahrtzeit_restaurant_ziel == auftrag.geschaetzte_fahrtzeit_restaurant_ziel && Objects.equals(timestamp, auftrag.timestamp) && Objects.equals(timestamp_On_Customer_Demand, auftrag.timestamp_On_Customer_Demand) && Objects.equals(lieferdistanz, auftrag.lieferdistanz) && Objects.equals(status, auftrag.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(auftrags_ID, auftragnehmer, timestamp, timestamp_On_Customer_Demand, anschrift, lieferdistanz, kundennummer, status, geschaetzte_fahrtzeit_restaurant_ziel);
    }

    @Override
    public String toString() {
        return "Auftrag{" +
                "auftrags_ID=" + auftrags_ID +
                ", auftragnehmer=" + auftragnehmer +
                ", timestamp=" + timestamp +
                ", timestamp_On_Customer_Demand=" + timestamp_On_Customer_Demand +
                ", anschrift=" + anschrift +
                ", lieferdistanz=" + lieferdistanz +
                ", kundennummer=" + kundennummer +
                ", status='" + status + '\'' +
                ", geschaetzte_fahrzeit_restaurant_ziel=" + geschaetzte_fahrtzeit_restaurant_ziel +
                '}';
    }
}