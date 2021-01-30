package de.eat4speed;

import javax.persistence.*;

@Entity
@Table(name = "Adressen", schema = "eatforspeed", catalog = "")
public class AdressenEntity {
    private Long id;
    private int adressId;
    private String strasse;
    private int hausnummer;
    private String ort;
    private int postleitzahl;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "Adress_ID")
    public int getAdressId() {
        return adressId;
    }

    public void setAdressId(int adressId) {
        this.adressId = adressId;
    }

    @Basic
    @Column(name = "Strasse")
    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    @Basic
    @Column(name = "Hausnummer")
    public int getHausnummer() {
        return hausnummer;
    }

    public void setHausnummer(int hausnummer) {
        this.hausnummer = hausnummer;
    }

    @Basic
    @Column(name = "Ort")
    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    @Basic
    @Column(name = "Postleitzahl")
    public int getPostleitzahl() {
        return postleitzahl;
    }

    public void setPostleitzahl(int postleitzahl) {
        this.postleitzahl = postleitzahl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdressenEntity that = (AdressenEntity) o;

        if (adressId != that.adressId) return false;
        if (hausnummer != that.hausnummer) return false;
        if (postleitzahl != that.postleitzahl) return false;
        if (strasse != null ? !strasse.equals(that.strasse) : that.strasse != null) return false;
        if (ort != null ? !ort.equals(that.ort) : that.ort != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = adressId;
        result = 31 * result + (strasse != null ? strasse.hashCode() : 0);
        result = 31 * result + hausnummer;
        result = 31 * result + (ort != null ? ort.hashCode() : 0);
        result = 31 * result + postleitzahl;
        return result;
    }
}
