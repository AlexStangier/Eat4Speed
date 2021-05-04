package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Favoritenliste_Gerichte extends PanacheEntityBase implements Serializable {

    public Favoritenliste_Gerichte(){}

    @Id
    private int gericht_ID;
    @Id
    private int kundennummer;
    private Timestamp hinzufuegedatum;
    private int anzahl_Bestellungen;

    public int getGericht_ID() {
        return gericht_ID;
    }

    public void setGericht_ID(int gericht_ID) {
        this.gericht_ID = gericht_ID;
    }

    public int getKundennummer() {
        return kundennummer;
    }

    public void setKundennummer(int kundennummer) {
        this.kundennummer = kundennummer;
    }

    public Timestamp getHinzufuegedatum() {
        return hinzufuegedatum;
    }

    public void setHinzufuegedatum(Timestamp hinzufuegedatum) {
        this.hinzufuegedatum = hinzufuegedatum;
    }

    public int getAnzahl_Bestellungen() {
        return anzahl_Bestellungen;
    }

    public void setAnzahl_Bestellungen(int anzahl_Bestellungen) {
        this.anzahl_Bestellungen = anzahl_Bestellungen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favoritenliste_Gerichte that = (Favoritenliste_Gerichte) o;
        return gericht_ID == that.gericht_ID && kundennummer == that.kundennummer && anzahl_Bestellungen == that.anzahl_Bestellungen && Objects.equals(hinzufuegedatum, that.hinzufuegedatum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gericht_ID, kundennummer, hinzufuegedatum, anzahl_Bestellungen);
    }

    @Override
    public String toString() {
        return "Favoritenliste_Gerichte{" +
                "gericht_ID=" + gericht_ID +
                ", kundennummer=" + kundennummer +
                ", hinzufuegedatum=" + hinzufuegedatum +
                ", anzahl_Bestellungen=" + anzahl_Bestellungen +
                '}';
    }
}
