package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Bestellzuordnung", schema = "eatforspeed")
@IdClass(BestellzuordnungEntityPK.class)
public class BestellzuordnungEntity extends PanacheEntityBase implements Serializable {
    private Long id;
    private int bestellId;
    private int gerichtId;

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

    @Id
    @Column(name = "Gericht_ID")
    public int getGerichtId() {
        return gerichtId;
    }

    public void setGerichtId(int gerichtId) {
        this.gerichtId = gerichtId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BestellzuordnungEntity that = (BestellzuordnungEntity) o;

        if (bestellId != that.bestellId) return false;
        if (gerichtId != that.gerichtId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bestellId;
        result = 31 * result + gerichtId;
        return result;
    }
}
