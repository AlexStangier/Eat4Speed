package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class Urlaub extends PanacheEntityBase implements Serializable {
    @Id
    @GeneratedValue
    private int urlaubs_id;
    private Date anfang;
    private Date ende;
    private int fahrernummer;

    public long getUrlaubs_id() {
        return urlaubs_id;
    }
    public void setUrlaubs_id(int urlaubs_id) {
        this.urlaubs_id = urlaubs_id;
    }

    public int getFahrernummer() { return fahrernummer; }
    public void setFahrernummer(int fahrernummer) { this.fahrernummer = fahrernummer; }

    public Date getAnfang() {
        return anfang;
    }
    public void setAnfang(Date anfang) {
        this.anfang = anfang;
    }

    public Date getEnde() {
        return ende;
    }
    public void setEnde(Date ende) {
        this.ende = ende;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Urlaub that = (Urlaub) o;

        if (urlaubs_id != that.urlaubs_id) return false;
        if (anfang != null ? !anfang.equals(that.anfang) : that.anfang != null) return false;
        if (ende != null ? !ende.equals(that.ende) : that.ende != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = urlaubs_id;
        result = 31 * result + (anfang != null ? anfang.hashCode() : 0);
        result = 31 * result + (ende != null ? ende.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UrlaubEntity{" +
                ", urlaubs_id=" + urlaubs_id +
                ", anfang=" + anfang +
                ", ende=" + ende +
                ", fahrernummer=" + fahrernummer +
                '}';
    }
}
