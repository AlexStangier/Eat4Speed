package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Bestellung extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue
    private int bestell_ID;
    private int bestellhistorien_ID;
    private int auftrags_ID;
    private Timestamp timestamp;
    private int rechnung;

    public int getBestell_ID() {
        return bestell_ID;
    }

    public void setBestell_ID(int bestellId) {
        this.bestell_ID = bestellId;
    }


    public int getBestellhistorien_ID() {
        return bestellhistorien_ID;
    }

    public void setBestellhistorien_ID(int bestellhistorienId) {
        this.bestellhistorien_ID = bestellhistorienId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }


    public Integer getRechnung() {
        return rechnung;
    }

    public void setRechnung(Integer rechnung) {
        this.rechnung = rechnung;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bestellung that = (Bestellung) o;
        return bestell_ID == that.bestell_ID && bestellhistorien_ID == that.bestellhistorien_ID && auftrags_ID == that.auftrags_ID && rechnung == that.rechnung && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bestell_ID, bestellhistorien_ID, auftrags_ID, timestamp, rechnung);
    }

    @Override
    public String toString() {
        return "Bestellung{" +
                "bestell_ID=" + bestell_ID +
                ", bestellhistorien_ID=" + bestellhistorien_ID +
                ", auftrags_ID=" + auftrags_ID +
                ", timestamp=" + timestamp +
                ", rechnung=" + rechnung +
                '}';
    }
}
