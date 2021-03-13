package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Auftrag extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue
    private long auftrags_Id;
    private int auftragnehmer;
    private Timestamp timestamp;
    private int anschrift;
    private Double lieferdistanz;
    private int kundenNummer;
    private String status;
    private int geschaetzte_fahrzeit_restaurant_ziel;
    private Timestamp timestamp_on_status_abegeholt;

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

    public long getAuftrags_Id() {
        return auftrags_Id;
    }

    public void setAuftrags_Id(long auftrags_Id) {
        this.auftrags_Id = auftrags_Id;
    }

    public int getKundenNummer() {
        return kundenNummer;
    }

    public void setKundenNummer(int kundenNummer) {
        this.kundenNummer = kundenNummer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getGeschaetzte_fahrzeit_restaurant_ziel() {
        return geschaetzte_fahrzeit_restaurant_ziel;
    }

    public void setGeschaetzte_fahrzeit_restaurant_ziel(int geschaetzte_fahrzeit_restaurant_ziel) {
        this.geschaetzte_fahrzeit_restaurant_ziel = geschaetzte_fahrzeit_restaurant_ziel;
    }

    public Timestamp getTimestamp_on_status_abegeholt() {
        return timestamp_on_status_abegeholt;
    }

    public void setTimestamp_on_status_abegeholt(Timestamp timestamp_on_status_abegeholt) {
        this.timestamp_on_status_abegeholt = timestamp_on_status_abegeholt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auftrag auftrag = (Auftrag) o;
        return auftrags_Id == auftrag.auftrags_Id && auftragnehmer == auftrag.auftragnehmer && anschrift == auftrag.anschrift && kundenNummer == auftrag.kundenNummer && geschaetzte_fahrzeit_restaurant_ziel == auftrag.geschaetzte_fahrzeit_restaurant_ziel && timestamp.equals(auftrag.timestamp) && Objects.equals(lieferdistanz, auftrag.lieferdistanz) && status.equals(auftrag.status) && Objects.equals(timestamp_on_status_abegeholt, auftrag.timestamp_on_status_abegeholt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(auftrags_Id, auftragnehmer, timestamp, anschrift, lieferdistanz, kundenNummer, status, geschaetzte_fahrzeit_restaurant_ziel, timestamp_on_status_abegeholt);
    }

    @Override
    public String toString() {
        return "Auftrag{" +
                "auftrags_Id=" + auftrags_Id +
                ", auftragnehmer=" + auftragnehmer +
                ", timestamp=" + timestamp +
                ", anschrift=" + anschrift +
                ", lieferdistanz=" + lieferdistanz +
                ", kundenNummer=" + kundenNummer +
                ", status='" + status + '\'' +
                ", geschaetzte_fahrzeit_restaurant_ziel=" + geschaetzte_fahrzeit_restaurant_ziel +
                ", timestamp_on_status_abegeholt=" + timestamp_on_status_abegeholt +
                '}';
    }
}
