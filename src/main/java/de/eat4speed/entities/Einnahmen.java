package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Einnahmen extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue
    private int einnahmen_Id;
    private int restaurant_Id;
    private double umsatz;
    private int monat;
    private int jahr;

    public int getEinnahmen_Id() {
        return einnahmen_Id;
    }

    public void setEinnahmen_Id(int einnahmenId) {
        this.einnahmen_Id = einnahmenId;
    }


    public int getRestaurant_Id() {
        return restaurant_Id;
    }

    public void setRestaurant_Id(int restaurantId) {
        this.restaurant_Id = restaurantId;
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

        if (einnahmen_Id != that.einnahmen_Id) return false;
        if (restaurant_Id != that.restaurant_Id) return false;
        if (Double.compare(that.umsatz, umsatz) != 0) return false;
        if (monat != that.monat) return false;
        if (jahr != that.jahr) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = einnahmen_Id;
        result = 31 * result + restaurant_Id;
        temp = Double.doubleToLongBits(umsatz);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + monat;
        result = 31 * result + jahr;
        return result;
    }

    @Override
    public String toString() {
        return "Einnahmen{" +
                "einnahmen_Id=" + einnahmen_Id +
                ", restaurant_Id=" + restaurant_Id +
                ", umsatz=" + umsatz +
                ", monat=" + monat +
                ", jahr=" + jahr +
                '}';
    }
}