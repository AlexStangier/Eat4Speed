package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Restaurant extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue
    private int restaurant_ID;
    private int benutzer_ID;
    private String name_des_Restaurants;
    private String allgemeine_Beschreibung;
    private int anschrift;
    private double mindestbestellwert;
    private double bestellradius;
    private byte verifiziert;


    public int getRestaurant_ID() {
        return restaurant_ID;
    }

    public void setRestaurant_ID(int restaurant_ID) {
        this.restaurant_ID = restaurant_ID;
    }

    public int getBenutzer_ID() {
        return benutzer_ID;
    }

    public void setBenutzer_ID(int benutzer_ID) {
        this.benutzer_ID = benutzer_ID;
    }

    public String getName_des_Restaurants() {
        return name_des_Restaurants;
    }

    public void setName_des_Restaurants(String name_des_Restaurants) {
        this.name_des_Restaurants = name_des_Restaurants;
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

    public double getMindestbestellwert() {
        return mindestbestellwert;
    }

    public void setMindestbestellwert(double mindestbestellwert) {
        this.mindestbestellwert = mindestbestellwert;
    }

    public double getBestellradius() {
        return bestellradius;
    }

    public void setBestellradius(double bestellradius) {
        this.bestellradius = bestellradius;
    }

    public byte getVerifiziert() {
        return verifiziert;
    }

    public void setVerifiziert(byte verifiziert) {
        this.verifiziert = verifiziert;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return restaurant_ID == that.restaurant_ID && benutzer_ID == that.benutzer_ID && anschrift == that.anschrift && Double.compare(that.mindestbestellwert, mindestbestellwert) == 0 && Double.compare(that.bestellradius, bestellradius) == 0 && verifiziert == that.verifiziert && Objects.equals(name_des_Restaurants, that.name_des_Restaurants) && Objects.equals(allgemeine_Beschreibung, that.allgemeine_Beschreibung);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurant_ID, benutzer_ID, name_des_Restaurants, allgemeine_Beschreibung, anschrift, mindestbestellwert, bestellradius, verifiziert);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurant_ID=" + restaurant_ID +
                ", benutzer_ID=" + benutzer_ID +
                ", name_des_Restaurants='" + name_des_Restaurants + '\'' +
                ", allgemeine_Beschreibung='" + allgemeine_Beschreibung + '\'' +
                ", anschrift=" + anschrift +
                ", mindestbestellwert=" + mindestbestellwert +
                ", bestellradius=" + bestellradius +
                ", verifiziert=" + verifiziert +
                '}';
    }
}
