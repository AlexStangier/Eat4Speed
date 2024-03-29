package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Kunde extends PanacheEntityBase implements Serializable {

    public Kunde(){}

    public Kunde(int kundennummer, int benutzer_ID, String name, String vorname, String anrede, int anschrift) {
        this.kundennummer = kundennummer;
        this.benutzer_ID = benutzer_ID;
        this.name = name;
        this.vorname = vorname;
        this.anrede = anrede;
        this.anschrift = anschrift;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int kundennummer;
    private int benutzer_ID;
    private String name;
    private String vorname;
    private String anrede;
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

    public int getBenutzer_ID() {
        return benutzer_ID;
    }

    public void setBenutzer_ID(int benutzer_ID) {
        this.benutzer_ID = benutzer_ID;
    }

    public int getAnschrift() {
        return anschrift;
    }

    public void setAnschrift(int anschrift) {
        this.anschrift = anschrift;
    }

    public String getAnrede() {
        return anrede;
    }

    public void setAnrede(String anrede) {
        this.anrede = anrede;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kunde kunde = (Kunde) o;
        return kundennummer == kunde.kundennummer && benutzer_ID == kunde.benutzer_ID && anschrift == kunde.anschrift && Objects.equals(name, kunde.name) && Objects.equals(vorname, kunde.vorname) && Objects.equals(anrede, kunde.anrede);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kundennummer, benutzer_ID, name, vorname, anrede, anschrift);
    }

    @Override
    public String toString() {
        return "Kunde{" +
                "kundennummer=" + kundennummer +
                ", benutzer_ID=" + benutzer_ID +
                ", name='" + name + '\'' +
                ", vorname='" + vorname + '\'' +
                ", anrede='" + anrede + '\'' +
                ", anschrift=" + anschrift +
                '}';
    }
}
