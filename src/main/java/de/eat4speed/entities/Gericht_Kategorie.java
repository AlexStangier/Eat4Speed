package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Gericht_Kategorie extends PanacheEntityBase implements Serializable {
    @Id
    @GeneratedValue
    private int gericht_id;
    private int kategorie_id;


    public int getGericht_id() {
        return gericht_id;
    }
    public void setGericht_id(int gerichtId) {
        this.gericht_id = gerichtId;
    }


    public int getKategorie_id() {
        return kategorie_id;
    }
    public void setKategorie_id(int kategorieId) {
        this.kategorie_id = kategorieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gericht_Kategorie that = (Gericht_Kategorie) o;

        if (gericht_id != that.gericht_id) return false;
        if (kategorie_id != that.kategorie_id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = gericht_id;
        result = 31 * result + kategorie_id;
        return result;
    }

    @Override
    public String toString() {
        return "GerichtKategorieEntity{" +
                "gericht_id=" + gericht_id +
                ", kategorie_id=" + kategorie_id +
                '}';
    }
}
