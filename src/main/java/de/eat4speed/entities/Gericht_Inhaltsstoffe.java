package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Gericht_Inhaltsstoffe extends PanacheEntityBase implements Serializable {
    @Id
    private int gericht_id;
    private String inhaltsstoff;
    private Integer menge;

    public int getGericht_id() {
        return gericht_id;
    }
    public void setGericht_id(int gericht_id) {
        this.gericht_id = gericht_id;
    }


    public String getInhaltsstoff() {
        return inhaltsstoff;
    }
    public void setInhaltsstoff(String inhaltsstoff) {
        this.inhaltsstoff = inhaltsstoff;
    }


    public Integer getMenge() {
        return menge;
    }
    public void setMenge(Integer menge) {
        this.menge = menge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gericht_Inhaltsstoffe that = (Gericht_Inhaltsstoffe) o;

        if (gericht_id != that.gericht_id) return false;
        if (inhaltsstoff != null ? !inhaltsstoff.equals(that.inhaltsstoff) : that.inhaltsstoff != null) return false;
        if (menge != null ? !menge.equals(that.menge) : that.menge != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = gericht_id;
        result = 31 * result + (inhaltsstoff != null ? inhaltsstoff.hashCode() : 0);
        result = 31 * result + (menge != null ? menge.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GerichtInhaltsstoffeEntity{" +
                "gericht_id=" + gericht_id +
                ", inhaltsstoff='" + inhaltsstoff + '\'' +
                ", menge=" + menge +
                '}';
    }
}
