package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "Bestellung", schema = "eatforspeed")
public class BestellungEntity extends PanacheEntityBase implements Serializable {
    private Long id;
    private int bestellId;
    private int bestellhistorienId;
    private Timestamp timestamp;
    private Integer rechnung;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "Bestell_ID")
    public int getBestellId() {
        return bestellId;
    }

    public void setBestellId(int bestellId) {
        this.bestellId = bestellId;
    }

    @Basic
    @Column(name = "Bestellhistorien_ID")
    public int getBestellhistorienId() {
        return bestellhistorienId;
    }

    public void setBestellhistorienId(int bestellhistorienId) {
        this.bestellhistorienId = bestellhistorienId;
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
    @Column(name = "Rechnung")
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

        BestellungEntity that = (BestellungEntity) o;

        if (bestellId != that.bestellId) return false;
        if (bestellhistorienId != that.bestellhistorienId) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;
        if (rechnung != null ? !rechnung.equals(that.rechnung) : that.rechnung != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bestellId;
        result = 31 * result + bestellhistorienId;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (rechnung != null ? rechnung.hashCode() : 0);
        return result;
    }
}
