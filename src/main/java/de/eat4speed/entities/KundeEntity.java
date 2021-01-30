package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Kunde", schema = "eatforspeed")
public class KundeEntity extends PanacheEntityBase implements Serializable {
    private Long id;
    private int kundennummer;
    private String name;
    private String vorname;
    private String bestellhistorie;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "Kundennummer")
    public int getKundennummer() {
        return kundennummer;
    }

    public void setKundennummer(int kundennummer) {
        this.kundennummer = kundennummer;
    }

    @Basic
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Vorname")
    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    @Basic
    @Column(name = "Bestellhistorie")
    public String getBestellhistorie() {
        return bestellhistorie;
    }

    public void setBestellhistorie(String bestellhistorie) {
        this.bestellhistorie = bestellhistorie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KundeEntity that = (KundeEntity) o;

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
}
