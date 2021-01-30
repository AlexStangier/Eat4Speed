package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class GerichtAllergeneEntityPK extends PanacheEntityBase implements Serializable {
    private int gerichtId;
    private String allergen;

    @Column(name = "Gericht_ID")
    @Id
    public int getGerichtId() {
        return gerichtId;
    }

    public void setGerichtId(int gerichtId) {
        this.gerichtId = gerichtId;
    }

    @Column(name = "Allergen")
    @Id
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

        GerichtAllergeneEntityPK that = (GerichtAllergeneEntityPK) o;

        if (gerichtId != that.gerichtId) return false;
        if (allergen != null ? !allergen.equals(that.allergen) : that.allergen != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = gerichtId;
        result = 31 * result + (allergen != null ? allergen.hashCode() : 0);
        return result;
    }
}
