package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Bestellzuordnung extends PanacheEntityBase implements Serializable {

    @Id
    private int bestell_Id;
    @Id
    private int gericht_Id;

    public int getBestell_Id() {
        return bestell_Id;
    }

    public void setBestell_Id(int bestellId) {
        this.bestell_Id = bestellId;
    }


    public int getGericht_Id() {
        return gericht_Id;
    }

    public void setGericht_Id(int gerichtId) {
        this.gericht_Id = gerichtId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bestellzuordnung that = (Bestellzuordnung) o;

        if (bestell_Id != that.bestell_Id) return false;
        if (gericht_Id != that.gericht_Id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bestell_Id;
        result = 31 * result + gericht_Id;
        return result;
    }

    @Override
    public String toString() {
        return "Bestellzuordnung{" +
                "bestellId=" + bestell_Id +
                ", gerichtId=" + gericht_Id +
                '}';
    }
}
