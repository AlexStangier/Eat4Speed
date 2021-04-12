package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Bestellhistorie extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bestellhistorien_ID;

    public int getBestellhistorien_ID() {
        return bestellhistorien_ID;
    }

    public void setBestellhistorien_ID(int bestellhistorien_ID) {
        this.bestellhistorien_ID = bestellhistorien_ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bestellhistorie that = (Bestellhistorie) o;
        return bestellhistorien_ID == that.bestellhistorien_ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bestellhistorien_ID);
    }

    @Override
    public String toString() {
        return "Bestellhistorie{" +
                "bestellhistorien_ID=" + bestellhistorien_ID +
                '}';
    }
}
