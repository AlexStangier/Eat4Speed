package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Fahrer extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue
    private int fahrernummer;
    private String benutzername;
    private int geleistete_Fahrten;
    private byte ist_In_Pause;
    private int fahrzeug;

    public int getFahrernummer() {
        return fahrernummer;
    }

    public void setFahrernummer(int fahrernummer) {
        this.fahrernummer = fahrernummer;
    }


    public Integer getGeleisteteFahrten() {
        return geleistete_Fahrten;
    }

    public void setGeleisteteFahrten(Integer geleisteteFahrten) {
        this.geleistete_Fahrten = geleisteteFahrten;
    }


    public byte getIstInPause() {
        return ist_In_Pause;
    }

    public void setIstInPause(byte istInPause) {
        this.ist_In_Pause = istInPause;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public int getGeleistete_Fahrten() {
        return geleistete_Fahrten;
    }

    public void setGeleistete_Fahrten(int geleistete_Fahrten) {
        this.geleistete_Fahrten = geleistete_Fahrten;
    }

    public byte getIst_In_Pause() {
        return ist_In_Pause;
    }

    public void setIst_In_Pause(byte ist_In_Pause) {
        this.ist_In_Pause = ist_In_Pause;
    }

    public int getFahrzeug() {
        return fahrzeug;
    }

    public void setFahrzeug(int fahrzeug) {
        this.fahrzeug = fahrzeug;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fahrer that = (Fahrer) o;
        return fahrernummer == that.fahrernummer && geleistete_Fahrten == that.geleistete_Fahrten && ist_In_Pause == that.ist_In_Pause && fahrzeug == that.fahrzeug && Objects.equals(benutzername, that.benutzername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fahrernummer, benutzername, geleistete_Fahrten, ist_In_Pause, fahrzeug);
    }

    @Override
    public String toString() {
        return "FahrerEntity{" +
                "fahrernummer=" + fahrernummer +
                ", benutzername='" + benutzername + '\'' +
                ", geleistete_Fahrten=" + geleistete_Fahrten +
                ", ist_In_Pause=" + ist_In_Pause +
                ", fahrzeug=" + fahrzeug +
                '}';
    }
}
