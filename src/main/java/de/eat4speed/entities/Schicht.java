package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Schicht extends PanacheEntityBase implements Serializable {
    @Id
    @GeneratedValue
    private int schicht_ID;
    private int fahrernummer;
    private Timestamp anfang;
    private Timestamp ende;

    public int getSchicht_ID() {
        return schicht_ID;
    }

    public void setSchicht_ID(int schicht_ID) {
        this.schicht_ID = schicht_ID;
    }

    public int getFahrernummer() {
        return fahrernummer;
    }

    public void setFahrernummer(int fahrernummer) {
        this.fahrernummer = fahrernummer;
    }

    public Timestamp getAnfang() {
        return anfang;
    }

    public void setAnfang(Timestamp anfang) {
        this.anfang = anfang;
    }

    public Timestamp getEnde() {
        return ende;
    }

    public void setEnde(Timestamp ende) {
        this.ende = ende;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schicht schicht = (Schicht) o;
        return schicht_ID == schicht.schicht_ID && fahrernummer == schicht.fahrernummer && Objects.equals(anfang, schicht.anfang) && Objects.equals(ende, schicht.ende);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schicht_ID, fahrernummer, anfang, ende);
    }

    @Override
    public String toString() {
        return "Schicht{" +
                "schicht_ID=" + schicht_ID +
                ", fahrernummer=" + fahrernummer +
                ", anfang=" + anfang +
                ", ende=" + ende +
                '}';
    }
}
