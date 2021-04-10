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
    private int benutzer_ID;
    private String anrede;
    private int geleistete_Fahrten;
    private byte ist_In_Pause;
    private int fahrzeug;
    private int aktueller_Standort;
    private int anzahl_aktuelle_Auftraege;
    private byte verifiziert;
    private String fuehrerschein;
    private String geburtsdatum;

    public int getFahrernummer() {
        return fahrernummer;
    }

    public void setFahrernummer(int fahrernummer) {
        this.fahrernummer = fahrernummer;
    }

    public int getBenutzer_ID() { return benutzer_ID; }

    public void setBenutzer_ID(int benutzer_Id) { this.benutzer_ID = benutzer_Id; }

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

    public String getAnrede() {
        return anrede;
    }

    public void setAnrede(String anrede) {
        this.anrede = anrede;
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

    public int getAktueller_Standort() {
        return aktueller_Standort;
    }

    public void setAktueller_Standort(int aktueller_Standort) {
        this.aktueller_Standort = aktueller_Standort;
    }

    public int getAnzahl_aktuelle_Auftraege() {
        return anzahl_aktuelle_Auftraege;
    }

    public void setAnzahl_aktuelle_Auftraege(int anzahl_aktuelle_Auftraege) {
        this.anzahl_aktuelle_Auftraege = anzahl_aktuelle_Auftraege;
    }

    public byte getVerifiziert() {
        return verifiziert;
    }

    public void setVerifiziert(byte verifiziert) {
        this.verifiziert = verifiziert;
    }

    public String getFuehrerschein() { return fuehrerschein; }

    public void setFuehrerschein(String fuehrerschein) { this.fuehrerschein = fuehrerschein; }

    public String getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(String geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fahrer fahrer = (Fahrer) o;
        return fahrernummer == fahrer.fahrernummer && benutzer_ID == fahrer.benutzer_ID && geleistete_Fahrten == fahrer.geleistete_Fahrten && ist_In_Pause == fahrer.ist_In_Pause && fahrzeug == fahrer.fahrzeug && aktueller_Standort == fahrer.aktueller_Standort && anzahl_aktuelle_Auftraege == fahrer.anzahl_aktuelle_Auftraege && verifiziert == fahrer.verifiziert && Objects.equals(anrede, fahrer.anrede) && Objects.equals(fuehrerschein, fahrer.fuehrerschein) && Objects.equals(geburtsdatum, fahrer.geburtsdatum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fahrernummer, benutzer_ID, anrede, geleistete_Fahrten, ist_In_Pause, fahrzeug, aktueller_Standort, anzahl_aktuelle_Auftraege, verifiziert, fuehrerschein, geburtsdatum);
    }

    @Override
    public String toString() {
        return "Fahrer{" +
                "fahrernummer=" + fahrernummer +
                ", benutzer_ID=" + benutzer_ID +
                ", anrede='" + anrede + '\'' +
                ", geleistete_Fahrten=" + geleistete_Fahrten +
                ", ist_In_Pause=" + ist_In_Pause +
                ", fahrzeug=" + fahrzeug +
                ", aktueller_Standort=" + aktueller_Standort +
                ", anzahl_aktuelle_Auftraege=" + anzahl_aktuelle_Auftraege +
                ", verifiziert=" + verifiziert +
                ", fuehrerschein='" + fuehrerschein + '\'' +
                ", geburtsdatum='" + geburtsdatum + '\'' +
                '}';
    }
}
