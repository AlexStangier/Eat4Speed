package de.eat4speed;

import javax.persistence.*;

@Entity
@Table(name = "Fahrer", schema = "eatforspeed", catalog = "")
public class FahrerEntity {
    private Long id;
    private int fahrernummer;
    private Integer geleisteteFahrten;
    private byte istInPause;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "Fahrernummer")
    public int getFahrernummer() {
        return fahrernummer;
    }

    public void setFahrernummer(int fahrernummer) {
        this.fahrernummer = fahrernummer;
    }

    @Basic
    @Column(name = "Geleistete_Fahrten")
    public Integer getGeleisteteFahrten() {
        return geleisteteFahrten;
    }

    public void setGeleisteteFahrten(Integer geleisteteFahrten) {
        this.geleisteteFahrten = geleisteteFahrten;
    }

    @Basic
    @Column(name = "Ist_in_Pause")
    public byte getIstInPause() {
        return istInPause;
    }

    public void setIstInPause(byte istInPause) {
        this.istInPause = istInPause;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FahrerEntity that = (FahrerEntity) o;

        if (fahrernummer != that.fahrernummer) return false;
        if (istInPause != that.istInPause) return false;
        if (geleisteteFahrten != null ? !geleisteteFahrten.equals(that.geleisteteFahrten) : that.geleisteteFahrten != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fahrernummer;
        result = 31 * result + (geleisteteFahrten != null ? geleisteteFahrten.hashCode() : 0);
        result = 31 * result + (int) istInPause;
        return result;
    }
}
