package de.eat4speed;

import javax.persistence.*;

@Entity
@Table(name = "Einnahmen", schema = "eatforspeed", catalog = "")
public class EinnahmenEntity {
    private Long id;
    private int einnahmenId;
    private int restaurantId;
    private double umsatz;
    private int monat;
    private int jahr;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "Einnahmen_ID")
    public int getEinnahmenId() {
        return einnahmenId;
    }

    public void setEinnahmenId(int einnahmenId) {
        this.einnahmenId = einnahmenId;
    }

    @Basic
    @Column(name = "Restaurant_ID")
    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Basic
    @Column(name = "Umsatz")
    public double getUmsatz() {
        return umsatz;
    }

    public void setUmsatz(double umsatz) {
        this.umsatz = umsatz;
    }

    @Basic
    @Column(name = "Monat")
    public int getMonat() {
        return monat;
    }

    public void setMonat(int monat) {
        this.monat = monat;
    }

    @Basic
    @Column(name = "Jahr")
    public int getJahr() {
        return jahr;
    }

    public void setJahr(int jahr) {
        this.jahr = jahr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EinnahmenEntity that = (EinnahmenEntity) o;

        if (einnahmenId != that.einnahmenId) return false;
        if (restaurantId != that.restaurantId) return false;
        if (Double.compare(that.umsatz, umsatz) != 0) return false;
        if (monat != that.monat) return false;
        if (jahr != that.jahr) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = einnahmenId;
        result = 31 * result + restaurantId;
        temp = Double.doubleToLongBits(umsatz);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + monat;
        result = 31 * result + jahr;
        return result;
    }
}
