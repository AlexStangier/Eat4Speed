package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Benachrichtigung_Kunde extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue
    private int benachrichtigungs_Id;
    private int kunde_ID;
    private int gericht;
    private String benachrichtigung;
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

    public int getKunde_ID() {
        return kunde_ID;
    }

    public void setKunde_ID(int kunde_ID) {
        this.kunde_ID = kunde_ID;
    }

    public int getGericht() {
        return gericht;
    }

    public void setGericht(int gericht) {
        this.gericht = gericht;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Benachrichtigung_Kunde that = (Benachrichtigung_Kunde) o;
        return benachrichtigungs_Id == that.benachrichtigungs_Id && kunde_ID == that.kunde_ID && gericht == that.gericht && Objects.equals(benachrichtigung, that.benachrichtigung) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(benachrichtigungs_Id, kunde_ID, gericht, benachrichtigung, timestamp);
    }

    @Override
    public String toString() {
        return "Benachrichtigung_Kunde{" +
                "benachrichtigungs_Id=" + benachrichtigungs_Id +
                ", kunde_ID=" + kunde_ID +
                ", gericht=" + gericht +
                ", benachrichtigung='" + benachrichtigung + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
