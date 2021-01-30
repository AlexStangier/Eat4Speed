package de.eat4speed;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Benachrichtigung_Kunde", schema = "eatforspeed", catalog = "")
public class BenachrichtigungKundeEntity {
    private Long id;
    private int benachrichtigungsId;
    private String benachrichtigung;
    private Timestamp timestamp;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "Benachrichtigungs_ID")
    public int getBenachrichtigungsId() {
        return benachrichtigungsId;
    }

    public void setBenachrichtigungsId(int benachrichtigungsId) {
        this.benachrichtigungsId = benachrichtigungsId;
    }

    @Basic
    @Column(name = "Benachrichtigung")
    public String getBenachrichtigung() {
        return benachrichtigung;
    }

    public void setBenachrichtigung(String benachrichtigung) {
        this.benachrichtigung = benachrichtigung;
    }

    @Basic
    @Column(name = "Timestamp")
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BenachrichtigungKundeEntity that = (BenachrichtigungKundeEntity) o;

        if (benachrichtigungsId != that.benachrichtigungsId) return false;
        if (benachrichtigung != null ? !benachrichtigung.equals(that.benachrichtigung) : that.benachrichtigung != null)
            return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = benachrichtigungsId;
        result = 31 * result + (benachrichtigung != null ? benachrichtigung.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }
}
