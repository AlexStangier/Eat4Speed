package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "Auftrag", schema = "eatforspeed")
public class AuftragEntity  extends PanacheEntityBase implements Serializable {
    private Long id;
    private int auftragsId;
    private int auftragnehmer;
    private Timestamp timestamp;
    private int anschrift;
    private Double lieferdistanz;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "Auftrags_ID")
    public int getAuftragsId() {
        return auftragsId;
    }

    public void setAuftragsId(int auftragsId) {
        this.auftragsId = auftragsId;
    }

    @Basic
    @Column(name = "Auftragnehmer")
    public int getAuftragnehmer() {
        return auftragnehmer;
    }

    public void setAuftragnehmer(int auftragnehmer) {
        this.auftragnehmer = auftragnehmer;
    }

    @Basic
    @Column(name = "Timestamp")
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Basic
    @Column(name = "Anschrift")
    public int getAnschrift() {
        return anschrift;
    }

    public void setAnschrift(int anschrift) {
        this.anschrift = anschrift;
    }

    @Basic
    @Column(name = "Lieferdistanz")
    public Double getLieferdistanz() {
        return lieferdistanz;
    }

    public void setLieferdistanz(Double lieferdistanz) {
        this.lieferdistanz = lieferdistanz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuftragEntity that = (AuftragEntity) o;

        if (auftragsId != that.auftragsId) return false;
        if (auftragnehmer != that.auftragnehmer) return false;
        if (anschrift != that.anschrift) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;
        if (lieferdistanz != null ? !lieferdistanz.equals(that.lieferdistanz) : that.lieferdistanz != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = auftragsId;
        result = 31 * result + auftragnehmer;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + anschrift;
        result = 31 * result + (lieferdistanz != null ? lieferdistanz.hashCode() : 0);
        return result;
    }
}
