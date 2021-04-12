package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Einnahmen extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue
    private int einnahmen_ID;
    private int restaurant_ID;
    private double umsatz;
    private int monat;
    private int jahr;

    public int getEinnahmen_ID() {
        return einnahmen_ID;
    }

    public void setEinnahmen_ID(int einnahmenId) {
        this.einnahmen_ID = einnahmenId;
    }


    public int getRestaurant_ID() {
        return restaurant_ID;
    }

    public void setRestaurant_ID(int restaurantId) {
        this.restaurant_ID = restaurantId;
    }


    public double getUmsatz() {
        return umsatz;
    }

    public void setUmsatz(double umsatz) {
        this.umsatz = umsatz;
    }


    public int getMonat() {
        return monat;
    }

    public void setMonat(int monat) {
        this.monat = monat;
    }


    public int getJahr() {
        return jahr;
    }

    public void setJahr(int jahr) {
        this.jahr = jahr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Einnahmen that = (Einnahmen) o;

        if (einnahmen_ID != that.einnahmen_ID) return false;
        if (restaurant_ID != that.restaurant_ID) return false;
        if (Double.compare(that.umsatz, umsatz) != 0) return false;
        if (monat != that.monat) return false;
        if (jahr != that.jahr) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = einnahmen_ID;
        result = 31 * result + restaurant_ID;
        temp = Double.doubleToLongBits(umsatz);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + monat;
        result = 31 * result + jahr;
        return result;
    }

    @Override
    public String toString() {
        return "Einnahmen{" +
                "einnahmen_ID=" + einnahmen_ID +
                ", restaurant_ID=" + restaurant_ID +
                ", umsatz=" + umsatz +
                ", monat=" + monat +
                ", jahr=" + jahr +
                '}';
    }
}
