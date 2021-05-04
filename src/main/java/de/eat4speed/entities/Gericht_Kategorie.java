package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Gericht_Kategorie extends PanacheEntityBase implements Serializable {

    public Gericht_Kategorie(){}

    @Id
    private int gericht_ID;
    @Id
    private String kategorie;

    public int getGericht_ID() {
        return gericht_ID;
    }

    public void setGericht_ID(int gericht_ID) {
        this.gericht_ID = gericht_ID;
    }

    public String getKategorie() {
        return kategorie;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gericht_Kategorie that = (Gericht_Kategorie) o;
        return gericht_ID == that.gericht_ID && Objects.equals(kategorie, that.kategorie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gericht_ID, kategorie);
    }

    @Override
    public String toString() {
        return "Gericht_Kategorie{" +
                "gericht_ID=" + gericht_ID +
                ", kategorie='" + kategorie + '\'' +
                '}';
    }
}
