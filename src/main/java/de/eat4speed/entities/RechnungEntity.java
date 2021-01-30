package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "Rechnung", schema = "eatforspeed")
public class RechnungEntity extends PanacheEntityBase implements Serializable {
    private Long id;
    private int rechnungsId;
    private double betrag;
    private Timestamp rechnungsdatum;
    private byte zahlungseingang;
    private Timestamp datumZahlungseingang;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "Rechnungs_ID")
    public int getRechnungsId() {
        return rechnungsId;
    }

    public void setRechnungsId(int rechnungsId) {
        this.rechnungsId = rechnungsId;
    }

    @Basic
    @Column(name = "Betrag")
    public double getBetrag() {
        return betrag;
    }

    public void setBetrag(double betrag) {
        this.betrag = betrag;
    }

    @Basic
    @Column(name = "Rechnungsdatum")
    public Timestamp getRechnungsdatum() {
        return rechnungsdatum;
    }

    public void setRechnungsdatum(Timestamp rechnungsdatum) {
        this.rechnungsdatum = rechnungsdatum;
    }

    @Basic
    @Column(name = "Zahlungseingang")
    public byte getZahlungseingang() {
        return zahlungseingang;
    }

    public void setZahlungseingang(byte zahlungseingang) {
        this.zahlungseingang = zahlungseingang;
    }

    @Basic
    @Column(name = "Datum_Zahlungseingang")
    public Timestamp getDatumZahlungseingang() {
        return datumZahlungseingang;
    }

    public void setDatumZahlungseingang(Timestamp datumZahlungseingang) {
        this.datumZahlungseingang = datumZahlungseingang;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RechnungEntity that = (RechnungEntity) o;

        if (rechnungsId != that.rechnungsId) return false;
        if (Double.compare(that.betrag, betrag) != 0) return false;
        if (zahlungseingang != that.zahlungseingang) return false;
        if (rechnungsdatum != null ? !rechnungsdatum.equals(that.rechnungsdatum) : that.rechnungsdatum != null)
            return false;
        if (datumZahlungseingang != null ? !datumZahlungseingang.equals(that.datumZahlungseingang) : that.datumZahlungseingang != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = rechnungsId;
        temp = Double.doubleToLongBits(betrag);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (rechnungsdatum != null ? rechnungsdatum.hashCode() : 0);
        result = 31 * result + (int) zahlungseingang;
        result = 31 * result + (datumZahlungseingang != null ? datumZahlungseingang.hashCode() : 0);
        return result;
    }
}
