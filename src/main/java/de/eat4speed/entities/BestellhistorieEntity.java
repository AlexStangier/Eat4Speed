package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Bestellhistorie", schema = "eatforspeed")
public class BestellhistorieEntity extends PanacheEntityBase implements Serializable {
    private Long id;
    private int bestellhistorienId;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "Bestellhistorien_ID")
    public int getBestellhistorienId() {
        return bestellhistorienId;
    }

    public void setBestellhistorienId(int bestellhistorienId) {
        this.bestellhistorienId = bestellhistorienId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BestellhistorieEntity that = (BestellhistorieEntity) o;

        if (bestellhistorienId != that.bestellhistorienId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return bestellhistorienId;
    }
}
