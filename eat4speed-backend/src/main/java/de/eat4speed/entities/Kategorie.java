package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Kategorie extends PanacheEntityBase implements Serializable {

    public Kategorie(){}

    @Id
    private String name;
    private String beschreibung;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kategorie kategorie = (Kategorie) o;
        return Objects.equals(name, kategorie.name) && Objects.equals(beschreibung, kategorie.beschreibung);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, beschreibung);
    }

    @Override
    public String toString() {
        return "Kategorie{" +
                "name='" + name + '\'' +
                ", beschreibung='" + beschreibung + '\'' +
                '}';
    }
}
