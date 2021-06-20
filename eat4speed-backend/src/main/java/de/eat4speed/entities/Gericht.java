package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Gericht extends PanacheEntityBase implements Serializable {

    public enum AvailabilityStatus {
        NOT_AVAILABLE((byte) 0),
        AVAILABLE((byte) 1);

        private final byte value;

        public byte value() {
            return this.value;
        }

        AvailabilityStatus(byte value) {
            this.value = value;
        }
    }

    public enum MealType {
        MEAL((byte) 0),
        DRINK((byte) 1);

        private final byte value;

        public byte value() {
            return this.value;
        }

        MealType(byte value) {
            this.value = value;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gericht_ID;
    private int restaurant_ID;
    private String name;
    private String beschreibung;
    private String abbildung;
    private double preis;
    private byte verfuegbar;
    private byte ist_Getraenk;
    private byte geloescht;

    public Gericht() {

    }

    public Gericht(int restaurant_ID, String name, String beschreibung, String abbildung, double preis, byte verfuegbar, byte ist_Getraenk) {
        this.restaurant_ID = restaurant_ID;
        this.name = name;
        this.beschreibung = beschreibung;
        this.abbildung = abbildung;
        this.preis = preis;
        this.verfuegbar = verfuegbar;
        this.ist_Getraenk = ist_Getraenk;
    }

    public int getGericht_ID() {
        return gericht_ID;
    }

    public void setGericht_ID(int gericht_ID) {
        this.gericht_ID = gericht_ID;
    }

    public int getRestaurant_ID() {
        return restaurant_ID;
    }

    public void setRestaurant_ID(int restaurant_ID) {
        this.restaurant_ID = restaurant_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getAbbildung() {
        return abbildung;
    }

    public void setAbbildung(String abbildung) {
        this.abbildung = abbildung;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public byte getVerfuegbar() {
        return verfuegbar;
    }

    public void setVerfuegbar(byte verfuegbar) {
        this.verfuegbar = verfuegbar;
    }

    public byte getIst_Getraenk() {
        return ist_Getraenk;
    }

    public void setIst_Getraenk(byte ist_Getraenk) {
        this.ist_Getraenk = ist_Getraenk;
    }

    public byte getGeloescht() {
        return geloescht;
    }

    public void setGeloescht(byte geloescht) {
        this.geloescht = geloescht;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gericht gericht = (Gericht) o;
        return gericht_ID == gericht.gericht_ID && restaurant_ID == gericht.restaurant_ID && Double.compare(gericht.preis, preis) == 0 && verfuegbar == gericht.verfuegbar && ist_Getraenk == gericht.ist_Getraenk && geloescht == gericht.geloescht && Objects.equals(name, gericht.name) && Objects.equals(beschreibung, gericht.beschreibung) && Objects.equals(abbildung, gericht.abbildung);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gericht_ID, restaurant_ID, name, beschreibung, abbildung, preis, verfuegbar, ist_Getraenk, geloescht);
    }

    @Override
    public String toString() {
        return "Gericht{" +
                "gericht_ID=" + gericht_ID +
                ", restaurant_ID=" + restaurant_ID +
                ", name='" + name + '\'' +
                ", beschreibung='" + beschreibung + '\'' +
                ", abbildung='" + abbildung + '\'' +
                ", preis=" + preis +
                ", verfuegbar=" + verfuegbar +
                ", ist_Getraenk=" + ist_Getraenk +
                ", geloescht=" + geloescht +
                '}';
    }
}
