package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Adressen extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue
    private int adress_Id;
    private String strasse;
    private int hausnummer;
    private String ort;
    private int postleitzahl;

    public int getAdress_Id() {
        return adress_Id;
    }

    public void setAdress_Id(int adressId) {
        this.adress_Id = adressId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Adressen that = (Adressen) o;

        if (adress_Id != that.adress_Id) return false;
        if (hausnummer != that.hausnummer) return false;
        if (postleitzahl != that.postleitzahl) return false;
        if (strasse != null ? !strasse.equals(that.strasse) : that.strasse != null) return false;
        if (ort != null ? !ort.equals(that.ort) : that.ort != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = adress_Id;
        result = 31 * result + (strasse != null ? strasse.hashCode() : 0);
        result = 31 * result + hausnummer;
        result = 31 * result + (ort != null ? ort.hashCode() : 0);
        result = 31 * result + postleitzahl;
        return result;
    }

    @Override
    public String toString() {
        return "Adressen{" +
                "adress_Id=" + adress_Id +
                ", strasse='" + strasse + '\'' +
                ", hausnummer=" + hausnummer +
                ", ort='" + ort + '\'' +
                ", postleitzahl=" + postleitzahl +
                '}';
    }
}