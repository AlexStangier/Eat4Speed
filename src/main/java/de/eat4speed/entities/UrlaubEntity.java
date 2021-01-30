package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "Urlaub", schema = "eatforspeed")
public class UrlaubEntity extends PanacheEntityBase implements Serializable {
    private Long id;
    private int urlaubsId;
    private Date anfang;
    private Date ende;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "Urlaubs_ID")
    public int getUrlaubsId() {
        return urlaubsId;
    }

    public void setUrlaubsId(int urlaubsId) {
        this.urlaubsId = urlaubsId;
    }

    @Basic
    @Column(name = "Anfang")
    public Date getAnfang() {
        return anfang;
    }

    public void setAnfang(Date anfang) {
        this.anfang = anfang;
    }

    @Basic
    @Column(name = "Ende")
    public Date getEnde() {
        return ende;
    }

    public void setEnde(Date ende) {
        this.ende = ende;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UrlaubEntity that = (UrlaubEntity) o;

        if (urlaubsId != that.urlaubsId) return false;
        if (anfang != null ? !anfang.equals(that.anfang) : that.anfang != null) return false;
        if (ende != null ? !ende.equals(that.ende) : that.ende != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = urlaubsId;
        result = 31 * result + (anfang != null ? anfang.hashCode() : 0);
        result = 31 * result + (ende != null ? ende.hashCode() : 0);
        return result;
    }
}
