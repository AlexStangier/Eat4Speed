package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Bestellzuordnung extends PanacheEntityBase implements Serializable {

    @Id
    private int bestell_ID;
    @Id
    private int gericht_ID;

    public int getBestell_ID() {
        return bestell_ID;
    }

    public void setBestell_ID(int bestellId) {
        this.bestell_ID = bestellId;
    }


    public int getGericht_ID() {
        return gericht_ID;
    }

    public void setGericht_ID(int gerichtId) {
        this.gericht_ID = gerichtId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bestellzuordnung that = (Bestellzuordnung) o;

        if (bestell_ID != that.bestell_ID) return false;
        if (gericht_ID != that.gericht_ID) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bestell_ID;
        result = 31 * result + gericht_ID;
        return result;
    }

    @Override
    public String toString() {
        return "Bestellzuordnung{" +
                "bestell_ID=" + bestell_ID +
                ", gericht_ID=" + gericht_ID +
                '}';
    }
}
