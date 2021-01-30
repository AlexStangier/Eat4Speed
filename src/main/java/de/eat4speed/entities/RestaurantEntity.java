package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Restaurant", schema = "eatforspeed")
public class RestaurantEntity extends PanacheEntityBase implements Serializable {
    private Long id;
    private int restaurantId;
    private String nameDesRestaurants;
    private String allgemeineBeschreibung;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "Restaurant_ID")
    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Basic
    @Column(name = "Name_des_Restaurants")
    public String getNameDesRestaurants() {
        return nameDesRestaurants;
    }

    public void setNameDesRestaurants(String nameDesRestaurants) {
        this.nameDesRestaurants = nameDesRestaurants;
    }

    @Basic
    @Column(name = "Allgemeine_Beschreibung")
    public String getAllgemeineBeschreibung() {
        return allgemeineBeschreibung;
    }

    public void setAllgemeineBeschreibung(String allgemeineBeschreibung) {
        this.allgemeineBeschreibung = allgemeineBeschreibung;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestaurantEntity that = (RestaurantEntity) o;

        if (restaurantId != that.restaurantId) return false;
        if (nameDesRestaurants != null ? !nameDesRestaurants.equals(that.nameDesRestaurants) : that.nameDesRestaurants != null)
            return false;
        if (allgemeineBeschreibung != null ? !allgemeineBeschreibung.equals(that.allgemeineBeschreibung) : that.allgemeineBeschreibung != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = restaurantId;
        result = 31 * result + (nameDesRestaurants != null ? nameDesRestaurants.hashCode() : 0);
        result = 31 * result + (allgemeineBeschreibung != null ? allgemeineBeschreibung.hashCode() : 0);
        return result;
    }
}
