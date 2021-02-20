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
    private int bestell_Id;
    private int bestellhistorien_Id;
    private int auftrags_Id;
    private Timestamp timestamp;
    private int rechnung;

    public int getBestell_Id() {
        return bestell_Id;
    }

    public void setBestell_Id(int bestellId) {
        this.bestell_Id = bestellId;
    }


    public int getBestellhistorien_Id() {
        return bestellhistorien_Id;
    }

    public void setBestellhistorien_Id(int bestellhistorienId) {
        this.bestellhistorien_Id = bestellhistorienId;
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
        return bestell_Id == that.bestell_Id && bestellhistorien_Id == that.bestellhistorien_Id && rechnung == that.rechnung && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bestell_Id, bestellhistorien_Id, timestamp, rechnung);
    }

    @Override
    public String toString() {
        return "Bestellung{" +
                "bestell_Id=" + bestell_Id +
                ", bestellhistorien_Id=" + bestellhistorien_Id +
                ", timestamp=" + timestamp +
                ", rechnung=" + rechnung +
                '}';
    }
}
