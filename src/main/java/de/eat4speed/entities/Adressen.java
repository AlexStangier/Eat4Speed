package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Adressen extends PanacheEntityBase implements Serializable {

    public Adressen(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adress_ID;
    private String strasse;
    private int hausnummer;
    private String ort;
    private int postleitzahl;
    private String lng;
    private String lat;

    public int getAdress_ID() {
        return adress_ID;
    }

    public void setAdress_ID(int adress_ID) {
        this.adress_ID = adress_ID;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public int getHausnummer() {
        return hausnummer;
    }

    public void setHausnummer(int hausnummer) {
        this.hausnummer = hausnummer;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public int getPostleitzahl() {
        return postleitzahl;
    }

    public void setPostleitzahl(int postleitzahl) {
        this.postleitzahl = postleitzahl;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adressen adressen = (Adressen) o;
        return adress_ID == adressen.adress_ID && hausnummer == adressen.hausnummer && postleitzahl == adressen.postleitzahl && Objects.equals(strasse, adressen.strasse) && Objects.equals(ort, adressen.ort) && Objects.equals(lng, adressen.lng) && Objects.equals(lat, adressen.lat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adress_ID, strasse, hausnummer, ort, postleitzahl, lng, lat);
    }

    @Override
    public String toString() {
        return "Adressen{" +
                "adress_ID=" + adress_ID +
                ", strasse='" + strasse + '\'' +
                ", hausnummer=" + hausnummer +
                ", ort='" + ort + '\'' +
                ", postleitzahl=" + postleitzahl +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                '}';
    }
}
