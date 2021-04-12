package de.eat4speed.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Fahrtenplan_Station {

    @Id
    @GeneratedValue
    private int Stations_ID;
    
    private int Auftrag;
    private int Fahrer;
    private int Liefer_Abholadresse;
    private int Vorherige_Station;
    private int Naechste_Station;
    private int Fahrzeit_A_B;
    private int Distanz_zu_naechster_Station;
    private int Geschaetzte_Fahrtzeit;

    public int getStations_ID() {
        return Stations_ID;
    }

    public void setStations_ID(int stations_ID) {
        Stations_ID = stations_ID;
    }

    public int getAuftrag() {
        return Auftrag;
    }

    public void setAuftrag(int auftrag) {
        Auftrag = auftrag;
    }

    public int getFahrer() {
        return Fahrer;
    }

    public void setFahrer(int fahrer) {
        Fahrer = fahrer;
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

    public int getNaechste_Station() {
        return Naechste_Station;
    }

    public void setNaechste_Station(int naechste_Station) {
        Naechste_Station = naechste_Station;
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
        Fahrtenplan_Station that = (Fahrtenplan_Station) o;
        return Stations_ID == that.Stations_ID && Auftrag == that.Auftrag && Fahrer == that.Fahrer && Liefer_Abholadresse == that.Liefer_Abholadresse && Vorherige_Station == that.Vorherige_Station && Naechste_Station == that.Naechste_Station && Fahrzeit_A_B == that.Fahrzeit_A_B && Distanz_zu_naechster_Station == that.Distanz_zu_naechster_Station && Geschaetzte_Fahrtzeit == that.Geschaetzte_Fahrtzeit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Stations_ID, Auftrag, Fahrer, Liefer_Abholadresse, Vorherige_Station, Naechste_Station, Fahrzeit_A_B, Distanz_zu_naechster_Station, Geschaetzte_Fahrtzeit);
    }

    @Override
    public String toString() {
        return "Fahrtenplan_Station{" +
                "Stations_ID=" + Stations_ID +
                ", Auftrag=" + Auftrag +
                ", Fahrer=" + Fahrer +
                ", Liefer_Abholadresse=" + Liefer_Abholadresse +
                ", Vorherige_Station=" + Vorherige_Station +
                ", Naechste_Station=" + Naechste_Station +
                ", Fahrzeit_A_B=" + Fahrzeit_A_B +
                ", Distanz_zu_naechster_Station=" + Distanz_zu_naechster_Station +
                ", Geschaetzte_Fahrtzeit=" + Geschaetzte_Fahrtzeit +
                '}';
    }
}
