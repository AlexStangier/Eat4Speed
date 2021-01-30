package de.eat4speed;

import javax.persistence.*;

@Entity
@Table(name = "Gericht_Allergene", schema = "eatforspeed", catalog = "")
@IdClass(GerichtAllergeneEntityPK.class)
public class GerichtAllergeneEntity {
    private Long id;
    private int gerichtId;
    private String allergen;

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
    @Column(name = "Allergen")
    public String getAllergen() {
        return allergen;
    }

    public void setAllergen(String allergen) {
        this.allergen = allergen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GerichtAllergeneEntity that = (GerichtAllergeneEntity) o;

        if (gerichtId != that.gerichtId) return false;
        if (allergen != null ? !allergen.equals(that.allergen) : that.allergen != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = gerichtId;
        result = 31 * result + (allergen != null ? allergen.hashCode() : 0);
        return result;
    }
}
