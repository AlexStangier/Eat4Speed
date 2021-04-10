package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Status extends PanacheEntityBase implements Serializable {
    @Id
    private String status_name;
    private int rechnungs_ID;



    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String statusName) {
        this.status_name = statusName;
    }

    public int getRechnungs_ID() { return rechnungs_ID; }

    public void setRechnungs_ID(int rechnungs_id) { this.rechnungs_ID = rechnungs_id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Status that = (Status) o;

        if (status_name != null ? !status_name.equals(that.status_name) : that.status_name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return status_name != null ? status_name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "StatusEntity{" +
                "status_name='" + status_name + '\'' +
                ", rechnungs_ID=" + rechnungs_ID +
                '}';
    }
}
