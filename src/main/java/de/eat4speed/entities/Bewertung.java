package de.eat4speed.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Bewertung {

    @Id
    @GeneratedValue
    private int Bewertungs_ID;
    
    private int Kundennummer;
    private int Restaurant_ID;
    private int Sterne;
    private String Text;
    private Timestamp Datum;
    private byte wurde_gemeldet;

    public int getBewertungs_ID() {
        return Bewertungs_ID;
    }

    public void setBewertungs_ID(int bewertungs_ID) {
        Bewertungs_ID = bewertungs_ID;
    }

    public int getKundennummer() {
        return Kundennummer;
    }

    public void setKundennummer(int kundennummer) {
        Kundennummer = kundennummer;
    }

    public int getRestaurant_ID() {
        return Restaurant_ID;
    }

    public void setRestaurant_ID(int restaurant_ID) {
        Restaurant_ID = restaurant_ID;
    }

    public int getSterne() {
        return Sterne;
    }

    public void setSterne(int sterne) {
        Sterne = sterne;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public Timestamp getDatum() {
        return Datum;
    }

    public void setDatum(Timestamp datum) {
        Datum = datum;
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
        return Bewertungs_ID == bewertung.Bewertungs_ID && Kundennummer == bewertung.Kundennummer && Restaurant_ID == bewertung.Restaurant_ID && Sterne == bewertung.Sterne && wurde_gemeldet == bewertung.wurde_gemeldet && Objects.equals(Text, bewertung.Text) && Objects.equals(Datum, bewertung.Datum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Bewertungs_ID, Kundennummer, Restaurant_ID, Sterne, Text, Datum, wurde_gemeldet);
    }

    @Override
    public String toString() {
        return "Bewertung{" +
                "Bewertungs_ID=" + Bewertungs_ID +
                ", Kundennummer=" + Kundennummer +
                ", Restaurant_ID=" + Restaurant_ID +
                ", Sterne=" + Sterne +
                ", Text='" + Text + '\'' +
                ", Datum=" + Datum +
                ", wurde_gemeldet=" + wurde_gemeldet +
                '}';
    }
}
