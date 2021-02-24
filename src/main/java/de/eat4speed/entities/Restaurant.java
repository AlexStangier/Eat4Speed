package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Restaurant extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue
    private int restaurant_Id;
    private String benutzername;
    private String name_Des_Restaurants;
    private String allgemeine_Beschreibung;
    private int anschrift;


    public int getRestaurant_Id() {
        return restaurant_Id;
    }

    public void setRestaurant_Id(int restaurantId) {
        this.restaurant_Id = restaurantId;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getName_Des_Restaurants() {
        return name_Des_Restaurants;
    }

    public void setName_Des_Restaurants(String name_Des_Restaurants) {
        this.name_Des_Restaurants = name_Des_Restaurants;
    }

    public String getAllgemeine_Beschreibung() {
        return allgemeine_Beschreibung;
    }

    public void setAllgemeine_Beschreibung(String allgemeine_Beschreibung) {
        this.allgemeine_Beschreibung = allgemeine_Beschreibung;
    }

    public int getAnschrift() {
        return anschrift;
    }

    public void setAnschrift(int anschrift) {
        this.anschrift = anschrift;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantId=" + restaurant_Id +
                ", benutzername='" + benutzername + '\'' +
                ", name_Des_Restaurants='" + name_Des_Restaurants + '\'' +
                ", allgemeine_Beschreibung='" + allgemeine_Beschreibung + '\'' +
                ", anschrift=" + anschrift +
                '}';
    }
}
