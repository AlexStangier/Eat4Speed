package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Adressen extends PanacheEntityBase implements Serializable {

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

    public void setAdress_ID(int adressId) {
        this.adress_ID = adressId;
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

    public void setLng(String lng) { this.lng = lng; }

    public String getLng() { return lng; }

    public void setLat(String lat) { this.lat = lat; }

    public String getLat() { return lat; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Adressen that = (Adressen) o;

        if (adress_ID != that.adress_ID) return false;
        if (hausnummer != that.hausnummer) return false;
        if (postleitzahl != that.postleitzahl) return false;
        if (strasse != null ? !strasse.equals(that.strasse) : that.strasse != null) return false;
        if (ort != null ? !ort.equals(that.ort) : that.ort != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = adress_ID;
        result = 31 * result + (strasse != null ? strasse.hashCode() : 0);
        result = 31 * result + hausnummer;
        result = 31 * result + (ort != null ? ort.hashCode() : 0);
        result = 31 * result + postleitzahl;
        result = 31 * result + (lng != null ? ort.hashCode() : 0);
        result = 31 * result + (lat != null ? ort.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Adressen{" +
                "adress_ID=" + adress_ID +
                ", strasse='" + strasse + '\'' +
                ", hausnummer=" + hausnummer +
                ", ort='" + ort + '\'' +
                ", postleitzahl=" + postleitzahl +
                ", lng=" + lng +
                ", lat=" + lat +
                '}';
    }
}
