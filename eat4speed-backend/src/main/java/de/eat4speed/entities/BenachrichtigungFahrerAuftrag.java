package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class BenachrichtigungFahrerAuftrag extends PanacheEntityBase implements Serializable {

    public BenachrichtigungFahrerAuftrag(){}

    @Id
    private int benachrichtigungs_ID;
    @Id
    private int auftrags_ID;

    public int getBenachrichtigungs_ID() {
        return benachrichtigungs_ID;
    }

    public void setBenachrichtigungs_ID(int benachrichtigungs_ID) {
        this.benachrichtigungs_ID = benachrichtigungs_ID;
    }

    public int getAuftrags_ID() {
        return auftrags_ID;
    }

    public void setAuftrags_ID(int auftrags_ID) {
        this.auftrags_ID = auftrags_ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BenachrichtigungFahrerAuftrag that = (BenachrichtigungFahrerAuftrag) o;
        return benachrichtigungs_ID == that.benachrichtigungs_ID && auftrags_ID == that.auftrags_ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(benachrichtigungs_ID, auftrags_ID);
    }

    @Override
    public String toString() {
        return "BenachrichtigungFahrerAuftrag{" +
                "benachrichtigungs_ID=" + benachrichtigungs_ID +
                ", auftrags_ID=" + auftrags_ID +
                '}';
    }
}
