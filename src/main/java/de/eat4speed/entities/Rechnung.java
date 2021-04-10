package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
public class Rechnung extends PanacheEntityBase implements Serializable {
    @Id
    @GeneratedValue
    private int rechnungs_ID;
    private double betrag;
    private Timestamp rechnungsdatum;
    private byte zahlungseingang;
    private Timestamp datum_Zahlungseingang;


    public int getRechnungs_ID() {
        return rechnungs_ID;
    }
    public void setRechnungs_ID(int rechnungs_id) {
        this.rechnungs_ID = rechnungs_id;
    }


    public double getBetrag() {
        return betrag;
    }
    public void setBetrag(double betrag) {
        this.betrag = betrag;
    }

    public Timestamp getRechnungsdatum() {
        return rechnungsdatum;
    }
    public void setRechnungsdatum(Timestamp rechnungsdatum) {
        this.rechnungsdatum = rechnungsdatum;
    }

    public byte getZahlungseingang() {
        return zahlungseingang;
    }
    public void setZahlungseingang(byte zahlungseingang) {
        this.zahlungseingang = zahlungseingang;
    }

    public Timestamp getDatum_Zahlungseingang() {
        return datum_Zahlungseingang;
    }
    public void setDatum_Zahlungseingang(Timestamp datumZahlungseingang) { this.datum_Zahlungseingang = datumZahlungseingang; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rechnung that = (Rechnung) o;

        if (rechnungs_ID != that.rechnungs_ID) return false;
        if (Double.compare(that.betrag, betrag) != 0) return false;
        if (zahlungseingang != that.zahlungseingang) return false;
        if (rechnungsdatum != null ? !rechnungsdatum.equals(that.rechnungsdatum) : that.rechnungsdatum != null)
            return false;
        if (datum_Zahlungseingang != null ? !datum_Zahlungseingang.equals(that.datum_Zahlungseingang) : that.datum_Zahlungseingang != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = rechnungs_ID;
        temp = Double.doubleToLongBits(betrag);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (rechnungsdatum != null ? rechnungsdatum.hashCode() : 0);
        result = 31 * result + (int) zahlungseingang;
        result = 31 * result + (datum_Zahlungseingang != null ? datum_Zahlungseingang.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RechnungEntity{" +
                "rechnungs_ID=" + rechnungs_ID +
                ", betrag=" + betrag +
                ", rechnungsdatum=" + rechnungsdatum +
                ", zahlungseingang=" + zahlungseingang +
                ", datum_Zahlungseingang=" + datum_Zahlungseingang +
                '}';
    }
}
