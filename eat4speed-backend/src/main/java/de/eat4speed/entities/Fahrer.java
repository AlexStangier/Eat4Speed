package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Fahrer extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fahrernummer;
    private int benutzer_ID;
    private String anrede;
    private int geleistete_Fahrten;
    private byte ist_in_Pause;
    private int fahrzeug;
    private int aktueller_Standort;
    private int anzahl_Aktueller_Auftraege;
    private byte verifiziert;
    private String fuehrerschein;
    private String geburtsdatum;

    public int getFahrernummer() {
        return fahrernummer;
    }

    public void setFahrernummer(int fahrernummer) {
        this.fahrernummer = fahrernummer;
    }

    public int getBenutzer_ID() {
        return benutzer_ID;
    }

    public void setBenutzer_ID(int benutzer_ID) {
        this.benutzer_ID = benutzer_ID;
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

    public byte getIst_in_Pause() {
        return ist_in_Pause;
    }

    public void setIst_in_Pause(byte ist_in_Pause) {
        this.ist_in_Pause = ist_in_Pause;
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

    public int getAnzahl_Aktueller_Auftraege() {
        return anzahl_Aktueller_Auftraege;
    }

    public void setAnzahl_Aktueller_Auftraege(int anzahl_Aktueller_Auftraege) {
        this.anzahl_Aktueller_Auftraege = anzahl_Aktueller_Auftraege;
    }

    public byte getVerifiziert() {
        return verifiziert;
    }

    public void setVerifiziert(byte verifiziert) {
        this.verifiziert = verifiziert;
    }

    public String getFuehrerschein() {
        return fuehrerschein;
    }

    public void setFuehrerschein(String fuehrerschein) {
        this.fuehrerschein = fuehrerschein;
    }

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
        return fahrernummer == fahrer.fahrernummer && benutzer_ID == fahrer.benutzer_ID && geleistete_Fahrten == fahrer.geleistete_Fahrten && ist_in_Pause == fahrer.ist_in_Pause && fahrzeug == fahrer.fahrzeug && aktueller_Standort == fahrer.aktueller_Standort && anzahl_Aktueller_Auftraege == fahrer.anzahl_Aktueller_Auftraege && verifiziert == fahrer.verifiziert && Objects.equals(anrede, fahrer.anrede) && Objects.equals(fuehrerschein, fahrer.fuehrerschein) && Objects.equals(geburtsdatum, fahrer.geburtsdatum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fahrernummer, benutzer_ID, anrede, geleistete_Fahrten, ist_in_Pause, fahrzeug, aktueller_Standort, anzahl_Aktueller_Auftraege, verifiziert, fuehrerschein, geburtsdatum);
    }

    @Override
    public String toString() {
        return "Fahrer{" +
                "fahrernummer=" + fahrernummer +
                ", benutzer_ID=" + benutzer_ID +
                ", anrede='" + anrede + '\'' +
                ", geleistete_Fahrten=" + geleistete_Fahrten +
                ", ist_in_Pause=" + ist_in_Pause +
                ", fahrzeug=" + fahrzeug +
                ", aktueller_Standort=" + aktueller_Standort +
                ", anzahl_Aktueller_Auftraege=" + anzahl_Aktueller_Auftraege +
                ", verifiziert=" + verifiziert +
                ", fuehrerschein='" + fuehrerschein + '\'' +
                ", geburtsdatum='" + geburtsdatum + '\'' +
                '}';
    }
}
