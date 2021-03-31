package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Gericht_Kategorie extends PanacheEntityBase implements Serializable {
    @Id
    private int gericht_id;
    @Id
    private String kategorie_id;


    public int getGericht_id() {
        return gericht_id;
    }
    public void setGericht_id(int gerichtId) {
        this.gericht_id = gerichtId;
    }


    public String getKategorie_id() {
        return kategorie_id;
    }

    public void setKategorie_id(String kategorie_id) {
        this.kategorie_id = kategorie_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gericht_Kategorie that = (Gericht_Kategorie) o;
        return gericht_id == that.gericht_id && Objects.equals(kategorie_id, that.kategorie_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gericht_id, kategorie_id);
    }

    @Override
    public String toString() {
        return "Gericht_Kategorie{" +
                "gericht_id=" + gericht_id +
                ", kategorie_id='" + kategorie_id + '\'' +
                '}';
    }
}
