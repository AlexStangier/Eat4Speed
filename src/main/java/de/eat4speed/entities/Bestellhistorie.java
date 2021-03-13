package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Bestellhistorie extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue
    private int bestellhistorienId;

    public int getBestellhistorienId() {
        return bestellhistorienId;
    }

    public void setBestellhistorienId(int bestellhistorienId) {
        this.bestellhistorienId = bestellhistorienId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bestellhistorie that = (Bestellhistorie) o;
        return bestellhistorienId == that.bestellhistorienId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bestellhistorienId);
    }

    @Override
    public String toString() {
        return "Bestellhistorie{" +
                "bestellhistorienId=" + bestellhistorienId +
                '}';
    }
}
