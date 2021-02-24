package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Fahrzeug extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue
    private int fahrzeug_Id;
    private String fahrzeugtyp;

    public int getFahrzeug_Id() {
        return fahrzeug_Id;
    }

    public void setFahrzeug_Id(int fahrzeugId) {
        this.fahrzeug_Id = fahrzeugId;
    }

    @Basic
    public String getFahrzeugtyp() {
        return fahrzeugtyp;
    }

    public void setFahrzeugtyp(String fahrzeugtyp) {
        this.fahrzeugtyp = fahrzeugtyp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fahrzeug that = (Fahrzeug) o;

        if (fahrzeug_Id != that.fahrzeug_Id) return false;
        if (fahrzeugtyp != null ? !fahrzeugtyp.equals(that.fahrzeugtyp) : that.fahrzeugtyp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fahrzeug_Id;
        result = 31 * result + (fahrzeugtyp != null ? fahrzeugtyp.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FahrzeugEntity{" +
                "fahrzeugId=" + fahrzeug_Id +
                ", fahrzeugtyp='" + fahrzeugtyp + '\'' +
                '}';
    }
}
