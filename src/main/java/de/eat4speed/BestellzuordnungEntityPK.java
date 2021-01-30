package de.eat4speed;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class BestellzuordnungEntityPK implements Serializable {
    private int bestellId;
    private int gerichtId;

    @Column(name = "Bestell_ID")
    @Id
    public int getBestellId() {
        return bestellId;
    }

    public void setBestellId(int bestellId) {
        this.bestellId = bestellId;
    }

    @Column(name = "Gericht_ID")
    @Id
    public int getGerichtId() {
        return gerichtId;
    }

    public void setGerichtId(int gerichtId) {
        this.gerichtId = gerichtId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BestellzuordnungEntityPK that = (BestellzuordnungEntityPK) o;

        if (bestellId != that.bestellId) return false;
        if (gerichtId != that.gerichtId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bestellId;
        result = 31 * result + gerichtId;
        return result;
    }
}
