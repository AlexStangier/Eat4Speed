package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

@Entity
public class Gericht extends PanacheEntityBase implements Serializable {
    @Id
    @GeneratedValue
    private int gerichtId;
    private int restaurant_id;
    private String name;
    private String beschreibung;
    private byte[] abbildung;
    private double preis;
    private byte verfuegbar;


    public int getGerichtId() {
        return gerichtId;
    }
    public void setGerichtId(int gerichtId) {
        this.gerichtId = gerichtId;
    }

    public int getRestaurant_id() { return restaurant_id; }

    public void setRestaurant_id(int restaurant_id) { this.restaurant_id = restaurant_id; }

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

    public byte[] getAbbildung() {
        return abbildung;
    }
    public void setAbbildung(byte[] abbildung) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gericht that = (Gericht) o;

        if (gerichtId != that.gerichtId) return false;
        if (Double.compare(that.preis, preis) != 0) return false;
        if (verfuegbar != that.verfuegbar) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (beschreibung != null ? !beschreibung.equals(that.beschreibung) : that.beschreibung != null) return false;
        if (!Arrays.equals(abbildung, that.abbildung)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = gerichtId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (beschreibung != null ? beschreibung.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(abbildung);
        temp = Double.doubleToLongBits(preis);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) verfuegbar;
        return result;
    }

    @Override
    public String toString() {
        return "Gericht{" +
                "gerichtId=" + gerichtId +
                ", restaurant_id=" + restaurant_id +
                ", name='" + name + '\'' +
                ", beschreibung='" + beschreibung + '\'' +
                ", abbildung=" + Arrays.toString(abbildung) +
                ", preis=" + preis +
                ", verfuegbar=" + verfuegbar +
                '}';
    }
}
