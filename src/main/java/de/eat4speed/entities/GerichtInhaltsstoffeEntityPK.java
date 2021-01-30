package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class GerichtInhaltsstoffeEntityPK extends PanacheEntityBase implements Serializable {
    private int gerichtId;
    private String inhaltsstoff;

    @Column(name = "Gericht_ID")
    @Id
    public int getGerichtId() {
        return gerichtId;
    }

    public void setGerichtId(int gerichtId) {
        this.gerichtId = gerichtId;
    }

    @Column(name = "Inhaltsstoff")
    @Id
    public String getInhaltsstoff() {
        return inhaltsstoff;
    }

    public void setInhaltsstoff(String inhaltsstoff) {
        this.inhaltsstoff = inhaltsstoff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GerichtInhaltsstoffeEntityPK that = (GerichtInhaltsstoffeEntityPK) o;

        if (gerichtId != that.gerichtId) return false;
        if (inhaltsstoff != null ? !inhaltsstoff.equals(that.inhaltsstoff) : that.inhaltsstoff != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = gerichtId;
        result = 31 * result + (inhaltsstoff != null ? inhaltsstoff.hashCode() : 0);
        return result;
    }
}
