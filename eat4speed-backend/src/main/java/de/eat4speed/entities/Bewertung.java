package de.eat4speed.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Bewertung {

    public Bewertung(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bewertung_ID;
    
    private int kundennummer;
    private int restaurant_ID;
    private int sterne;
    private String text;
    private Timestamp datum;
    private byte wurde_gemeldet;

    public int getBewertung_ID() {
        return bewertung_ID;
    }

    public void setBewertung_ID(int bewertung_ID) {
        this.bewertung_ID = bewertung_ID;
    }

    public int getKundennummer() {
        return kundennummer;
    }

    public void setKundennummer(int kundennummer) {
        this.kundennummer = kundennummer;
    }

    public int getRestaurant_ID() {
        return restaurant_ID;
    }

    public void setRestaurant_ID(int restaurant_ID) {
        this.restaurant_ID = restaurant_ID;
    }

    public int getSterne() {
        return sterne;
    }

    public void setSterne(int sterne) {
        this.sterne = sterne;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getDatum() {
        return datum;
    }

    public void setDatum(Timestamp datum) {
        this.datum = datum;
    }

    public byte getWurde_gemeldet() {
        return wurde_gemeldet;
    }

    public void setWurde_gemeldet(byte wurde_gemeldet) {
        this.wurde_gemeldet = wurde_gemeldet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bewertung bewertung = (Bewertung) o;
        return bewertung_ID == bewertung.bewertung_ID && kundennummer == bewertung.kundennummer && restaurant_ID == bewertung.restaurant_ID && sterne == bewertung.sterne && wurde_gemeldet == bewertung.wurde_gemeldet && Objects.equals(text, bewertung.text) && Objects.equals(datum, bewertung.datum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bewertung_ID, kundennummer, restaurant_ID, sterne, text, datum, wurde_gemeldet);
    }

    @Override
    public String toString() {
        return "Bewertung{" +
                "bewertung_ID=" + bewertung_ID +
                ", kundennummer=" + kundennummer +
                ", restaurant_ID=" + restaurant_ID +
                ", sterne=" + sterne +
                ", text='" + text + '\'' +
                ", datum=" + datum +
                ", wurde_gemeldet=" + wurde_gemeldet +
                '}';
    }
}
