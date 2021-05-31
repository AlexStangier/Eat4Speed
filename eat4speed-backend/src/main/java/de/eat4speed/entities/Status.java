package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Status extends PanacheEntityBase implements Serializable {

    public Status(){}

    public Status(int rechnungs_ID) {
        this.status_Name = "offen";
        this.rechnungs_ID = rechnungs_ID;
    }

    @Id
    private String status_Name;
    private int rechnungs_ID;

    public String getStatus_Name() {
        return status_Name;
    }

    public void setStatus_Name(String status_Name) {
        this.status_Name = status_Name;
    }

    public int getRechnungs_ID() { return rechnungs_ID; }

    public void setRechnungs_ID(int rechnungs_ID) { this.rechnungs_ID = rechnungs_ID; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Status that = (Status) o;

        if (status_Name != null ? !status_Name.equals(that.status_Name) : that.status_Name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return status_Name != null ? status_Name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "StatusEntity{" +
                "status_Name='" + status_Name + '\'' +
                ", rechnungs_ID=" + rechnungs_ID +
                '}';
    }
}
