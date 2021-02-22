package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Gericht_Allergene extends PanacheEntityBase implements Serializable {
    @Id
    private int gericht_id;
    private String allergen;


    public int getGericht_id() {
        return gericht_id;
    }
    public void setGericht_id(int gericht_id) {
        this.gericht_id = gericht_id;
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

        if (gericht_id != that.gericht_id) return false;
        if (allergen != null ? !allergen.equals(that.allergen) : that.allergen != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = gericht_id;
        result = 31 * result + (allergen != null ? allergen.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GerichtAllergeneEntity{" +
                "gericht_id=" + gericht_id +
                ", allergen='" + allergen + '\'' +
                '}';
    }
}