package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Restaurant_Zeiten extends PanacheEntityBase implements Serializable {
    @Id
    private int restaurant_id;
    @Id
    private int oeffnungszeiten_id;

    public int getRestaurant_id() {
        return restaurant_id;
    }
    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public int getOeffnungszeiten_id() { return oeffnungszeiten_id; }
    public void setOeffnungszeiten_id(int oeffnungszeiten_id) { this.oeffnungszeiten_id = oeffnungszeiten_id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Restaurant_Zeiten that = (Restaurant_Zeiten) o;

        if (restaurant_id != that.restaurant_id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return restaurant_id;
    }

    @Override
    public String toString() {
        return "RestaurantZeitenEntity{" +
                "restaurant_id=" + restaurant_id +
                ", oeffnungszeiten_id=" + oeffnungszeiten_id +
                '}';
    }
}
