package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Bestellung extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bestell_ID;
    private Integer auftrags_ID;
    private Timestamp timestamp;
    private Integer rechnung;
    private String Gericht_IDs;
    private String status;
    private Integer restaurant_ID;

    public Bestellung() {
    }

    public Bestellung(int auftrags_ID, Timestamp timestamp, int rechnung, String status, int restaurant) {
        this.auftrags_ID = auftrags_ID;
        this.timestamp = timestamp;
        this.rechnung = rechnung;
        this.status = status;
        this.restaurant_ID = restaurant;
    }


    public int getBestell_ID() {
        return bestell_ID;
    }

    public void setBestell_ID(int bestell_ID) {
        this.bestell_ID = bestell_ID;
    }

    public int getAuftrags_ID() {
        return auftrags_ID;
    }

    public void setAuftrags_ID(int auftrags_ID) {
        this.auftrags_ID = auftrags_ID;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getRechnung() {
        return rechnung;
    }

    public void setRechnung(int rechnung) {
        this.rechnung = rechnung;
    }

    public String getGericht_IDs() {
        return Gericht_IDs;
    }

    public void setGericht_IDs(String gericht_IDs) {
        Gericht_IDs = gericht_IDs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRestaurant_ID() {
        return restaurant_ID;
    }

    public void setRestaurant_ID(int restaurant_ID) {
        this.restaurant_ID = restaurant_ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bestellung that = (Bestellung) o;
        return bestell_ID == that.bestell_ID && auftrags_ID == that.auftrags_ID && rechnung == that.rechnung && restaurant_ID == that.restaurant_ID && Objects.equals(timestamp, that.timestamp) && Objects.equals(Gericht_IDs, that.Gericht_IDs) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bestell_ID, auftrags_ID, timestamp, rechnung, Gericht_IDs, status, restaurant_ID);
    }

    @Override
    public String toString() {
        return "Bestellung{" +
                "bestell_ID=" + bestell_ID +
                ", auftrags_ID=" + auftrags_ID +
                ", timestamp=" + timestamp +
                ", rechnung=" + rechnung +
                ", Gericht_IDs='" + Gericht_IDs + '\'' +
                ", status='" + status + '\'' +
                ", restaurant_ID=" + restaurant_ID +
                '}';
    }
}
