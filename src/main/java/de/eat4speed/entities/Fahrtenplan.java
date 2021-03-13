package de.eat4speed.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Fahrtenplan {

    @Id
    @GeneratedValue
    private int Fahrtenplan_ID;
    
    private int Auftrag_ID;
    private int Fahrernummer;
    private int Liefer_Abholadresse;
    private int Vorherige_Station;
    private int Fahrzeit_A_B;
    private int Distanz_zu_naechster_Station;
    private int Geschaetzte_Fahrtzeit;

    public int getFahrtenplan_ID() {
        return Fahrtenplan_ID;
    }

    public void setFahrtenplan_ID(int fahrtenplan_ID) {
        Fahrtenplan_ID = fahrtenplan_ID;
    }

    public int getAuftrag_ID() {
        return Auftrag_ID;
    }

    public void setAuftrag_ID(int auftrag_ID) {
        Auftrag_ID = auftrag_ID;
    }

    public int getFahrernummer() {
        return Fahrernummer;
    }

    public void setFahrernummer(int fahrernummer) {
        Fahrernummer = fahrernummer;
    }

    public int getLiefer_Abholadresse() {
        return Liefer_Abholadresse;
    }

    public void setLiefer_Abholadresse(int liefer_Abholadresse) {
        Liefer_Abholadresse = liefer_Abholadresse;
    }

    public int getVorherige_Station() {
        return Vorherige_Station;
    }

    public void setVorherige_Station(int vorherige_Station) {
        Vorherige_Station = vorherige_Station;
    }

    public int getFahrzeit_A_B() {
        return Fahrzeit_A_B;
    }

    public void setFahrzeit_A_B(int fahrzeit_A_B) {
        Fahrzeit_A_B = fahrzeit_A_B;
    }

    public int getDistanz_zu_naechster_Station() {
        return Distanz_zu_naechster_Station;
    }

    public void setDistanz_zu_naechster_Station(int distanz_zu_naechster_Station) {
        Distanz_zu_naechster_Station = distanz_zu_naechster_Station;
    }

    public int getGeschaetzte_Fahrtzeit() {
        return Geschaetzte_Fahrtzeit;
    }

    public void setGeschaetzte_Fahrtzeit(int geschaetzte_Fahrtzeit) {
        Geschaetzte_Fahrtzeit = geschaetzte_Fahrtzeit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fahrtenplan that = (Fahrtenplan) o;
        return Fahrtenplan_ID == that.Fahrtenplan_ID && Auftrag_ID == that.Auftrag_ID
                && Fahrernummer == that.Fahrernummer && Liefer_Abholadresse == that.Liefer_Abholadresse
                && Vorherige_Station == that.Vorherige_Station && Fahrzeit_A_B == that.Fahrzeit_A_B
                && Distanz_zu_naechster_Station == that.Distanz_zu_naechster_Station
                && Geschaetzte_Fahrtzeit == that.Geschaetzte_Fahrtzeit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Fahrtenplan_ID, Auftrag_ID, Fahrernummer, Liefer_Abholadresse,
                Vorherige_Station, Fahrzeit_A_B, Distanz_zu_naechster_Station, Geschaetzte_Fahrtzeit);
    }

    @Override
    public String toString() {
        return "Fahrtenplan{" +
                "Fahrtenplan_ID=" + Fahrtenplan_ID +
                ", Auftrag_ID=" + Auftrag_ID +
                ", Fahrernummer=" + Fahrernummer +
                ", Liefer_Abholadresse=" + Liefer_Abholadresse +
                ", Vorherige_Station=" + Vorherige_Station +
                ", Fahrzeit_A_B=" + Fahrzeit_A_B +
                ", Distanz_zu_naechster_Station=" + Distanz_zu_naechster_Station +
                ", Geschaetzte_Fahrtzeit=" + Geschaetzte_Fahrtzeit +
                '}';
    }
}
