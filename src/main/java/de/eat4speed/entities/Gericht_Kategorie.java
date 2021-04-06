package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Gericht_Kategorie extends PanacheEntityBase implements Serializable {
    @Id
    private int gericht_ID;
    @Id
    private String name;

    public int getGericht_ID() {
        return gericht_ID;
    }

    public void setGericht_ID(int gericht_id) {
        this.gericht_ID = gericht_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gericht_Kategorie that = (Gericht_Kategorie) o;
        return gericht_ID == that.gericht_ID && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gericht_ID, name);
    }

    @Override
    public String toString() {
        return "Gericht_Kategorie{" +
                "gericht_ID=" + gericht_ID +
                ", name='" + name + '\'' +
                '}';
    }
}
