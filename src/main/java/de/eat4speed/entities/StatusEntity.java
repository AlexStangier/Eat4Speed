package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Status", schema = "eatforspeed")
public class StatusEntity extends PanacheEntityBase implements Serializable {
    private Long id;
    private String statusName;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "Status_Name")
    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatusEntity that = (StatusEntity) o;

        if (statusName != null ? !statusName.equals(that.statusName) : that.statusName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return statusName != null ? statusName.hashCode() : 0;
    }
}
