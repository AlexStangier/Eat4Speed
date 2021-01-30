package de.eat4speed;

import javax.persistence.*;

@Entity
@Table(name = "Gericht_Inhaltsstoffe", schema = "eatforspeed", catalog = "")
@IdClass(GerichtInhaltsstoffeEntityPK.class)
public class GerichtInhaltsstoffeEntity {
    private Long id;
    private int gerichtId;
    private String inhaltsstoff;
    private Integer menge;

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

    @Id
    @Column(name = "Inhaltsstoff")
    public String getInhaltsstoff() {
        return inhaltsstoff;
    }

    public void setInhaltsstoff(String inhaltsstoff) {
        this.inhaltsstoff = inhaltsstoff;
    }

    @Basic
    @Column(name = "Menge")
    public Integer getMenge() {
        return menge;
    }

    public void setMenge(Integer menge) {
        this.menge = menge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GerichtInhaltsstoffeEntity that = (GerichtInhaltsstoffeEntity) o;

        if (gerichtId != that.gerichtId) return false;
        if (inhaltsstoff != null ? !inhaltsstoff.equals(that.inhaltsstoff) : that.inhaltsstoff != null) return false;
        if (menge != null ? !menge.equals(that.menge) : that.menge != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = gerichtId;
        result = 31 * result + (inhaltsstoff != null ? inhaltsstoff.hashCode() : 0);
        result = 31 * result + (menge != null ? menge.hashCode() : 0);
        return result;
    }
}
