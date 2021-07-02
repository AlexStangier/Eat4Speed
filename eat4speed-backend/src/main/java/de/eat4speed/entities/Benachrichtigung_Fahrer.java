package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Benachrichtigung_Fahrer extends PanacheEntityBase implements Serializable {

    public Benachrichtigung_Fahrer(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int benachrichtigungs_ID;
    private int fahrernummer;
    private String benachrichtigung;
    private Integer restaurant_ID;
    private Timestamp timestamp;
    private byte gelesen;
    private int auftrags_ID;

    public Benachrichtigung_Fahrer(int benachrichtigungs_ID, int fahrernummer, String benachrichtigung,
                                   Integer restaurant_ID, Timestamp timestamp, byte gelesen, int auftrags_ID)
    {
        this.benachrichtigungs_ID = benachrichtigungs_ID;
        this.fahrernummer = fahrernummer;
        this.benachrichtigung = benachrichtigung;
        this.restaurant_ID = restaurant_ID;
        this.timestamp = timestamp;
        this.gelesen = gelesen;
        this.auftrags_ID = auftrags_ID;
    }

    public int getBenachrichtigungs_ID() {
        return benachrichtigungs_ID;
    }

    public void setBenachrichtigungs_ID(int benachrichtigungs_ID) {
        this.benachrichtigungs_ID = benachrichtigungs_ID;
    }

    public int getFahrernummer() {
        return fahrernummer;
    }

    public void setFahrernummer(int fahrernummer) {
        this.fahrernummer = fahrernummer;
    }

    public String getBenachrichtigung() {
        return benachrichtigung;
    }

    public void setBenachrichtigung(String benachrichtigung) {
        this.benachrichtigung = benachrichtigung;
    }

    public int getRestaurant_ID() {
        return restaurant_ID;
    }

    public void setRestaurant_ID(int restaurant_ID) {
        this.restaurant_ID = restaurant_ID;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public byte getGelesen() {
        return gelesen;
    }

    public void setGelesen(byte gelesen) {
        this.gelesen = gelesen;
    }

    public void setRestaurant_ID(Integer restaurant_ID) {
        this.restaurant_ID = restaurant_ID;
    }

    public int getAuftrags_ID() {
        return auftrags_ID;
    }

    public void setAuftrags_ID(int auftrags_ID) {
        this.auftrags_ID = auftrags_ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Benachrichtigung_Fahrer that = (Benachrichtigung_Fahrer) o;
        return benachrichtigungs_ID == that.benachrichtigungs_ID && fahrernummer == that.fahrernummer && gelesen == that.gelesen && auftrags_ID == that.auftrags_ID && Objects.equals(benachrichtigung, that.benachrichtigung) && Objects.equals(restaurant_ID, that.restaurant_ID) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(benachrichtigungs_ID, fahrernummer, benachrichtigung, restaurant_ID, timestamp, gelesen, auftrags_ID);
    }

    @Override
    public String toString() {
        return "Benachrichtigung_Fahrer{" +
                "benachrichtigungs_ID=" + benachrichtigungs_ID +
                ", fahrernummer=" + fahrernummer +
                ", benachrichtigung='" + benachrichtigung + '\'' +
                ", restaurant_ID=" + restaurant_ID +
                ", timestamp=" + timestamp +
                ", gelesen=" + gelesen +
                ", auftrags_ID=" + auftrags_ID +
                '}';
    }

    public JSONObject toJSON() {

        return new JSONObject()
                .put("benachrichtigungs_ID", this.benachrichtigungs_ID)
                .put("fahrernummer", this.fahrernummer)
                .put("benachrichtigung", this.benachrichtigung)
                .put("restaurant_ID", this.restaurant_ID)
                .put("timestamp", this.timestamp)
                .put("auftrags_ID", this.auftrags_ID);
    }

    public static Benachrichtigung_Fahrer fromJSON(String data)
    {
        JSONObject obj = new JSONObject(data);
        Benachrichtigung_Fahrer b = new Benachrichtigung_Fahrer();

        b.setBenachrichtigungs_ID(obj.getInt("benachrichtigungs_ID"));
        b.setFahrernummer(obj.getInt("fahrernummer"));
        b.setBenachrichtigung(obj.getString("benachrichtigung"));
        b.setRestaurant_ID((Integer) obj.opt("restaurant_ID"));
        b.setTimestamp(Timestamp.valueOf(obj.getString("timestamp")));
        b.setAuftrags_ID(obj.getInt("auftrags_ID"));

        return b;
    }
}
