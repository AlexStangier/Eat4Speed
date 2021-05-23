package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class EntfernungKundeRestaurant extends PanacheEntityBase implements Serializable {

    public EntfernungKundeRestaurant(){}

    @Id
    private int kundennummer;
    @Id
    private int restaurant_ID;
    private double entfernung;

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

    public double getEntfernung() {
        return entfernung;
    }

    public void setEntfernung(double entfernung) {
        this.entfernung = entfernung;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntfernungKundeRestaurant that = (EntfernungKundeRestaurant) o;
        return kundennummer == that.kundennummer && restaurant_ID == that.restaurant_ID && Double.compare(that.entfernung, entfernung) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(kundennummer, restaurant_ID, entfernung);
    }

    @Override
    public String toString() {
        return "EntfernungKundeRestaurant{" +
                "kundennummer=" + kundennummer +
                ", restaurant_ID=" + restaurant_ID +
                ", entfernung=" + entfernung +
                '}';
    }
}
