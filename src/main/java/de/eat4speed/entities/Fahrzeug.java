package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Fahrzeug extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue
    private int fahrzeug_ID;
    private String fahrzeugtyp;
    private int kapazitaet_Gerichte;

    public int getFahrzeug_ID() {
        return fahrzeug_ID;
    }

    public void setFahrzeug_ID(int fahrzeugId) {
        this.fahrzeug_ID = fahrzeugId;
    }

    @Basic
    public String getFahrzeugtyp() {
        return fahrzeugtyp;
    }

    public void setFahrzeugtyp(String fahrzeugtyp) {
        this.fahrzeugtyp = fahrzeugtyp;
    }

    public int getKapazitaet_Gerichte() {
        return kapazitaet_Gerichte;
    }

    public void setKapazitaet_Gerichte(int kapazitaet_Gerichte) {
        this.kapazitaet_Gerichte = kapazitaet_Gerichte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fahrzeug fahrzeug = (Fahrzeug) o;
        return fahrzeug_ID == fahrzeug.fahrzeug_ID && kapazitaet_Gerichte == fahrzeug.kapazitaet_Gerichte && Objects.equals(fahrzeugtyp, fahrzeug.fahrzeugtyp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fahrzeug_ID, fahrzeugtyp, kapazitaet_Gerichte);
    }

    @Override
    public String toString() {
        return "Fahrzeug{" +
                "fahrzeug_ID=" + fahrzeug_ID +
                ", fahrzeugtyp='" + fahrzeugtyp + '\'' +
                ", kapazitaet_Gerichte=" + kapazitaet_Gerichte +
                '}';
    }
}
