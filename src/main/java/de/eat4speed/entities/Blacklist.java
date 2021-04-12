package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Blacklist extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eintrag_ID;
    private String emailAdresse;
    private String loeschbegruendung;

    public int getEintrag_ID() {
        return eintrag_ID;
    }

    public void setEintrag_ID(int eintrag_ID) {
        this.eintrag_ID = eintrag_ID;
    }

    public String getEmailAdresse() {
        return emailAdresse;
    }

    public void setEmailAdresse(String emailAdresse) {
        this.emailAdresse = emailAdresse;
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
        return eintrag_ID == blacklist.eintrag_ID && Objects.equals(emailAdresse, blacklist.emailAdresse) && Objects.equals(loeschbegruendung, blacklist.loeschbegruendung);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eintrag_ID, emailAdresse, loeschbegruendung);
    }

    @Override
    public String toString() {
        return "Blacklist{" +
                "eintrag_ID=" + eintrag_ID +
                ", emailAdresse='" + emailAdresse + '\'' +
                ", loeschbegruendung='" + loeschbegruendung + '\'' +
                '}';
    }
}
