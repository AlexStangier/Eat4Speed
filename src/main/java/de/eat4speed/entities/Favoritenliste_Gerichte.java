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

    @Id
    private int restaurant_ID;
    @Id
    private int kundennummer;
    private Timestamp hinzufuegedatum;
    private int anzahl_Bestellungen;

    public int getRestaurant_ID() {
        return restaurant_ID;
    }

    public void setRestaurant_ID(int restaurant_ID) {
        this.restaurant_ID = restaurant_ID;
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
        return restaurant_ID == that.restaurant_ID && kundennummer == that.kundennummer && anzahl_Bestellungen == that.anzahl_Bestellungen && Objects.equals(hinzufuegedatum, that.hinzufuegedatum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurant_ID, kundennummer, hinzufuegedatum, anzahl_Bestellungen);
    }

    @Override
    public String toString() {
        return "Favoritenliste_Gerichte{" +
                "restaurant_ID=" + restaurant_ID +
                ", kundennummer=" + kundennummer +
                ", hinzufuegedatum=" + hinzufuegedatum +
                ", anzahl_Bestellungen=" + anzahl_Bestellungen +
                '}';
    }
}
