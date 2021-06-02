package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class Urlaub extends PanacheEntityBase implements Serializable {

    public Urlaub(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int urlaubs_ID;
    private Date anfang;
    private Date ende;
    private int fahrernummer;

    public long getUrlaubs_ID() {
        return urlaubs_ID;
    }
    public void setUrlaubs_ID(int urlaubs_ID) {
        this.urlaubs_ID = urlaubs_ID;
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

        if (urlaubs_ID != that.urlaubs_ID) return false;
        if (anfang != null ? !anfang.equals(that.anfang) : that.anfang != null) return false;
        if (ende != null ? !ende.equals(that.ende) : that.ende != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = urlaubs_ID;
        result = 31 * result + (anfang != null ? anfang.hashCode() : 0);
        result = 31 * result + (ende != null ? ende.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UrlaubEntity{" +
                ", urlaubs_ID=" + urlaubs_ID +
                ", anfang=" + anfang +
                ", ende=" + ende +
                ", fahrernummer=" + fahrernummer +
                '}';
    }
}
