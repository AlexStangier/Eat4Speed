package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Blacklist extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue
    private int eintrag_ID;
    private String eMailAdresse;
    private String loeschbegruendung;

    public int getEintrag_ID() {
        return eintrag_ID;
    }

    public void setEintrag_ID(int eintrag_ID) {
        this.eintrag_ID = eintrag_ID;
    }

    public String geteMailAdresse() {
        return eMailAdresse;
    }

    public void seteMailAdresse(String eMailAdresse) {
        this.eMailAdresse = eMailAdresse;
    }

    public String getLoeschbegruendung() {
        return loeschbegruendung;
    }

    public void setLoeschbegruendung(String loeschbegruendung) {
        this.loeschbegruendung = loeschbegruendung;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blacklist blacklist = (Blacklist) o;
        return eintrag_ID == blacklist.eintrag_ID && Objects.equals(eMailAdresse, blacklist.eMailAdresse) && Objects.equals(loeschbegruendung, blacklist.loeschbegruendung);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eintrag_ID, eMailAdresse, loeschbegruendung);
    }

    @Override
    public String toString() {
        return "Blacklist{" +
                "eintrag_ID=" + eintrag_ID +
                ", eMailAdresse='" + eMailAdresse + '\'' +
                ", loeschbegruendung='" + loeschbegruendung + '\'' +
                '}';
    }
}
