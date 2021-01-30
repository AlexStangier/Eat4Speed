package de.eat4speed;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "Gericht", schema = "eatforspeed", catalog = "")
public class GerichtEntity {
    private Long id;
    private int gerichtId;
    private String name;
    private String beschreibung;
    private byte[] abbildung;
    private double preis;
    private byte verfuegbar;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "Gericht_ID")
    public int getGerichtId() {
        return gerichtId;
    }

    public void setGerichtId(int gerichtId) {
        this.gerichtId = gerichtId;
    }

    @Basic
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Beschreibung")
    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    @Basic
    @Column(name = "Abbildung")
    public byte[] getAbbildung() {
        return abbildung;
    }

    public void setAbbildung(byte[] abbildung) {
        this.abbildung = abbildung;
    }

    @Basic
    @Column(name = "Preis")
    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    @Basic
    @Column(name = "Verfuegbar")
    public byte getVerfuegbar() {
        return verfuegbar;
    }

    public void setVerfuegbar(byte verfuegbar) {
        this.verfuegbar = verfuegbar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GerichtEntity that = (GerichtEntity) o;

        if (gerichtId != that.gerichtId) return false;
        if (Double.compare(that.preis, preis) != 0) return false;
        if (verfuegbar != that.verfuegbar) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (beschreibung != null ? !beschreibung.equals(that.beschreibung) : that.beschreibung != null) return false;
        if (!Arrays.equals(abbildung, that.abbildung)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = gerichtId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (beschreibung != null ? beschreibung.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(abbildung);
        temp = Double.doubleToLongBits(preis);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) verfuegbar;
        return result;
    }
}
