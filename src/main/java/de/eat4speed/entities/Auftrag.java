package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
public class Auftrag extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue
    private long auftrags_Id;
    private int auftragnehmer;
    private Timestamp timestamp;
    private int anschrift;
    private Double lieferdistanz;
    private int kundenNummer;
    private String status;

    public int getAuftragnehmer() {
        return auftragnehmer;
    }

    public void setAuftragnehmer(int auftragnehmer) {
        this.auftragnehmer = auftragnehmer;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getAnschrift() {
        return anschrift;
    }

    public void setAnschrift(int anschrift) {
        this.anschrift = anschrift;
    }

    public Double getLieferdistanz() {
        return lieferdistanz;
    }

    public void setLieferdistanz(Double lieferdistanz) {
        this.lieferdistanz = lieferdistanz;
    }

    public long getAuftrags_Id() {
        return auftrags_Id;
    }

    public void setAuftrags_Id(long auftrags_Id) {
        this.auftrags_Id = auftrags_Id;
    }

    public int getKundenNummer() {
        return kundenNummer;
    }

    public void setKundenNummer(int kundenNummer) {
        this.kundenNummer = kundenNummer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Auftrag that = (Auftrag) o;

        if (auftrags_Id != that.auftrags_Id) return false;
        if (auftragnehmer != that.auftragnehmer) return false;
        if (anschrift != that.anschrift) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;
        if (lieferdistanz != null ? !lieferdistanz.equals(that.lieferdistanz) : that.lieferdistanz != null)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "Auftrag{" +
                "auftrags_Id=" + auftrags_Id +
                ", auftragnehmer=" + auftragnehmer +
                ", timestamp=" + timestamp +
                ", anschrift=" + anschrift +
                ", lieferdistanz=" + lieferdistanz +
                ", kundenNummer=" + kundenNummer +
                ", status='" + status + '\'' +
                '}';
    }
}
