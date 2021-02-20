package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Kategorie extends PanacheEntityBase implements Serializable {
    @Id
    @GeneratedValue
    private int kategorie_id;
    private String name;
    private String beschreibung;


    public int getKategorie_id() {
        return kategorie_id;
    }
    public void setKategorie_id(int kategorieId) {
        this.kategorie_id = kategorieId;
    }

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

        Kategorie that = (Kategorie) o;

        if (kategorie_id != that.kategorie_id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (beschreibung != null ? !beschreibung.equals(that.beschreibung) : that.beschreibung != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = kategorie_id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (beschreibung != null ? beschreibung.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "KategorieEntity{" +
                "kategorie_id=" + kategorie_id +
                ", name='" + name + '\'' +
                ", beschreibung='" + beschreibung + '\'' +
                '}';
    }
}
