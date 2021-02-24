package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Benachrichtigung_Fahrer extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue
    private int benachrichtigungs_Id;
    private int fahrernummer;
    private String benachrichtigung;
    private int restaurant_Id;
    private Timestamp timestamp;


    public int getBenachrichtigungs_Id() {
        return benachrichtigungs_Id;
    }

    public void setBenachrichtigungs_Id(int benachrichtigungsId) {
        this.benachrichtigungs_Id = benachrichtigungsId;
    }

    public String getBenachrichtigung() {
        return benachrichtigung;
    }

    public void setBenachrichtigung(String benachrichtigung) {
        this.benachrichtigung = benachrichtigung;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getFahrernummer() {
        return fahrernummer;
    }

    public void setFahrernummer(int fahrernummer) {
        this.fahrernummer = fahrernummer;
    }

    public int getRestaurant_Id() {
        return restaurant_Id;
    }

    public void setRestaurant_Id(int restaurant_Id) {
        this.restaurant_Id = restaurant_Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Benachrichtigung_Fahrer that = (Benachrichtigung_Fahrer) o;
        return benachrichtigungs_Id == that.benachrichtigungs_Id && fahrernummer == that.fahrernummer && restaurant_Id == that.restaurant_Id && Objects.equals(benachrichtigung, that.benachrichtigung) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(benachrichtigungs_Id, fahrernummer, benachrichtigung, restaurant_Id, timestamp);
    }

    @Override
    public String toString() {
        return "Benachrichtigung_Fahrer{" +
                "benachrichtigungs_Id=" + benachrichtigungs_Id +
                ", fahrernummer=" + fahrernummer +
                ", benachrichtigung='" + benachrichtigung + '\'' +
                ", restaurant_Id=" + restaurant_Id +
                ", timestamp=" + timestamp +
                '}';
    }
}
