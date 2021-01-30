package de.eat4speed;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "Oeffnungszeiten", schema = "eatforspeed", catalog = "")
public class OeffnungszeitenEntity {
    private Long id;
    private int oeffnungszeitenId;
    private Time anfang;
    private Time ende;
    private String wochentag;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "Oeffnungszeiten_ID")
    public int getOeffnungszeitenId() {
        return oeffnungszeitenId;
    }

    public void setOeffnungszeitenId(int oeffnungszeitenId) {
        this.oeffnungszeitenId = oeffnungszeitenId;
    }

    @Basic
    @Column(name = "Anfang")
    public Time getAnfang() {
        return anfang;
    }

    public void setAnfang(Time anfang) {
        this.anfang = anfang;
    }

    @Basic
    @Column(name = "Ende")
    public Time getEnde() {
        return ende;
    }

    public void setEnde(Time ende) {
        this.ende = ende;
    }

    @Basic
    @Column(name = "Wochentag")
    public String getWochentag() {
        return wochentag;
    }

    public void setWochentag(String wochentag) {
        this.wochentag = wochentag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OeffnungszeitenEntity that = (OeffnungszeitenEntity) o;

        if (oeffnungszeitenId != that.oeffnungszeitenId) return false;
        if (anfang != null ? !anfang.equals(that.anfang) : that.anfang != null) return false;
        if (ende != null ? !ende.equals(that.ende) : that.ende != null) return false;
        if (wochentag != null ? !wochentag.equals(that.wochentag) : that.wochentag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = oeffnungszeitenId;
        result = 31 * result + (anfang != null ? anfang.hashCode() : 0);
        result = 31 * result + (ende != null ? ende.hashCode() : 0);
        result = 31 * result + (wochentag != null ? wochentag.hashCode() : 0);
        return result;
    }
}
