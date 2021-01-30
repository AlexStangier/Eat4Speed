package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Gericht_Kategorie", schema = "eatforspeed")
@IdClass(GerichtKategorieEntityPK.class)
public class GerichtKategorieEntity extends PanacheEntityBase implements Serializable {
    private Long id;
    private int gerichtId;
    private int kategorieId;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "Gericht_ID")
    public int getGerichtId() {
        return gerichtId;
    }

    public void setGerichtId(int gerichtId) {
        this.gerichtId = gerichtId;
    }

    @Id
    @Column(name = "Kategorie_ID")
    public int getKategorieId() {
        return kategorieId;
    }

    public void setKategorieId(int kategorieId) {
        this.kategorieId = kategorieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GerichtKategorieEntity that = (GerichtKategorieEntity) o;

        if (gerichtId != that.gerichtId) return false;
        if (kategorieId != that.kategorieId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = gerichtId;
        result = 31 * result + kategorieId;
        return result;
    }
}
