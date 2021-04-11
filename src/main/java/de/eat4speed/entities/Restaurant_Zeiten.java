package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Restaurant_Zeiten extends PanacheEntityBase implements Serializable {
    @Id
    private int restaurant_ID;
    @Id
    private int oeffnungszeiten_ID;

    public int getRestaurant_ID() {
        return restaurant_ID;
    }
    public void setRestaurant_ID(int restaurant_ID) {
        this.restaurant_ID = restaurant_ID;
    }

    public int getOeffnungszeiten_ID() { return oeffnungszeiten_ID; }
    public void setOeffnungszeiten_ID(int oeffnungszeiten_ID) { this.oeffnungszeiten_ID = oeffnungszeiten_ID; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Restaurant_Zeiten that = (Restaurant_Zeiten) o;

        if (restaurant_ID != that.restaurant_ID) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return restaurant_ID;
    }

    @Override
    public String toString() {
        return "RestaurantZeitenEntity{" +
                "restaurant_ID=" + restaurant_ID +
                ", oeffnungszeiten_ID=" + oeffnungszeiten_ID +
                '}';
    }
}
