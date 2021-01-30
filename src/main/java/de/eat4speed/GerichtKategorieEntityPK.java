package de.eat4speed;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class GerichtKategorieEntityPK implements Serializable {
    private int gerichtId;
    private int kategorieId;

    @Column(name = "Gericht_ID")
    @Id
    public int getGerichtId() {
        return gerichtId;
    }

    public void setGerichtId(int gerichtId) {
        this.gerichtId = gerichtId;
    }

    @Column(name = "Kategorie_ID")
    @Id
    public int getKategorieId() {
        return kategorieId;
    }

    public void setKategorieId(int kategorieId) {
        this.kategorieId = kategorieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GerichtKategorieEntityPK that = (GerichtKategorieEntityPK) o;

        if (gerichtId != that.gerichtId) return false;
        if (kategorieId != that.kategorieId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = gerichtId;
        result = 31 * result + kategorieId;
        return result;
    }
}
