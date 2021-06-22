package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;


@Entity
public class Oeffnungszeiten extends PanacheEntityBase implements Serializable {

    public enum DayOfWeek {
        MONDAY("Montag"),
        TUESDAY("Dienstag"),
        WEDNESDAY("Mittwoch"),
        THURSDAY("Donnerstag"),
        FRIDAY("Freitag"),
        SATURDAY("Samstag"),
        SUNDAY("Sonntag");

        private final String value;

        public String value() {
            return this.value;
        }

        DayOfWeek(String value) {
            this.value = value;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int oeffnungszeiten_ID;
    private Timestamp anfang;
    private Timestamp ende;
    private String wochentag;
    private int restaurant_ID;

    public Oeffnungszeiten() {

    }

    public Oeffnungszeiten(Timestamp anfang, Timestamp ende, String wochentag, int restaurant_ID) {
        this.anfang = anfang;
        this.ende = ende;
        this.wochentag = wochentag;
        this.restaurant_ID = restaurant_ID;
    }

    public int getOeffnungszeiten_ID() {
        return oeffnungszeiten_ID;
    }
    public void setOeffnungszeiten_ID(int oeffnungszeitenId) {
        this.oeffnungszeiten_ID = oeffnungszeitenId;
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

    public String getWochentag() {
        return wochentag;
    }
    public void setWochentag(String wochentag) {
        this.wochentag = wochentag;
    }

    public int getRestaurant_ID() {return restaurant_ID;}
    public void setRestaurant_ID(int id) {this.restaurant_ID = id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Oeffnungszeiten that = (Oeffnungszeiten) o;

        if (oeffnungszeiten_ID != that.oeffnungszeiten_ID) return false;
        if (anfang != null ? !anfang.equals(that.anfang) : that.anfang != null) return false;
        if (ende != null ? !ende.equals(that.ende) : that.ende != null) return false;
        if (wochentag != null ? !wochentag.equals(that.wochentag) : that.wochentag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = oeffnungszeiten_ID;
        result = 31 * result + (anfang != null ? anfang.hashCode() : 0);
        result = 31 * result + (ende != null ? ende.hashCode() : 0);
        result = 31 * result + (wochentag != null ? wochentag.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OeffnungszeitenEntity{" +
                "oeffnungszeiten_ID=" + oeffnungszeiten_ID +
                ", anfang=" + anfang +
                ", ende=" + ende +
                ", wochentag='" + wochentag + ", restaurant_ID='" + restaurant_ID + '\'' +
                '}';
    }
}
