package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Kunde extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue
    private int kundennummer;
    private String benutzername;
    private String name;
    private String vorname;
    private String bestellhistorie;
    private int anschrift;


    public int getKundennummer() {
        return kundennummer;
    }

    public void setKundennummer(int kundennummer) {
        this.kundennummer = kundennummer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getBestellhistorie() {
        return bestellhistorie;
    }

    public void setBestellhistorie(String bestellhistorie) {
        this.bestellhistorie = bestellhistorie;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public int getAnschrift() {
        return anschrift;
    }

    public void setAnschrift(int anschrift) {
        this.anschrift = anschrift;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kunde that = (Kunde) o;

        if (kundennummer != that.kundennummer) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (vorname != null ? !vorname.equals(that.vorname) : that.vorname != null) return false;
        if (bestellhistorie != null ? !bestellhistorie.equals(that.bestellhistorie) : that.bestellhistorie != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = kundennummer;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (vorname != null ? vorname.hashCode() : 0);
        result = 31 * result + (bestellhistorie != null ? bestellhistorie.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Kunde{" +
                "kundennummer=" + kundennummer +
                ", benutzername='" + benutzername + '\'' +
                ", name='" + name + '\'' +
                ", vorname='" + vorname + '\'' +
                ", bestellhistorie='" + bestellhistorie + '\'' +
                ", anschrift=" + anschrift +
                '}';
    }
}
