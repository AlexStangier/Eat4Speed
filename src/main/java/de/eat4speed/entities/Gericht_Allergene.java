package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Gericht_Allergene extends PanacheEntityBase implements Serializable {
    @Id
    private int gericht_ID;
    @Id
    private String allergen;


    public int getGericht_ID() {
        return gericht_ID;
    }
    public void setGericht_ID(int gericht_id) {
        this.gericht_ID = gericht_id;
    }

    public String getAllergen() {
        return allergen;
    }
    public void setAllergen(String allergen) {
        this.allergen = allergen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gericht_Allergene that = (Gericht_Allergene) o;

        if (gericht_ID != that.gericht_ID) return false;
        if (allergen != null ? !allergen.equals(that.allergen) : that.allergen != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = gericht_ID;
        result = 31 * result + (allergen != null ? allergen.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GerichtAllergeneEntity{" +
                "gericht_ID=" + gericht_ID +
                ", allergen='" + allergen + '\'' +
                '}';
    }
}
