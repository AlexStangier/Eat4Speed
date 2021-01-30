package de.eat4speed;

import javax.persistence.*;

@Entity
@Table(name = "Kategorie", schema = "eatforspeed", catalog = "")
public class KategorieEntity {
    private Long id;
    private int kategorieId;
    private String name;
    private String beschreibung;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "Kategorie_ID")
    public int getKategorieId() {
        return kategorieId;
    }

    public void setKategorieId(int kategorieId) {
        this.kategorieId = kategorieId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KategorieEntity that = (KategorieEntity) o;

        if (kategorieId != that.kategorieId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (beschreibung != null ? !beschreibung.equals(that.beschreibung) : that.beschreibung != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = kategorieId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (beschreibung != null ? beschreibung.hashCode() : 0);
        return result;
    }
}
