package de.eat4speed;

import javax.persistence.*;

@Entity
@Table(name = "Fahrzeug", schema = "eatforspeed", catalog = "")
public class FahrzeugEntity {
    private Long id;
    private int fahrzeugId;
    private String fahrzeugtyp;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "Fahrzeug_ID")
    public int getFahrzeugId() {
        return fahrzeugId;
    }

    public void setFahrzeugId(int fahrzeugId) {
        this.fahrzeugId = fahrzeugId;
    }

    @Basic
    @Column(name = "Fahrzeugtyp")
    public String getFahrzeugtyp() {
        return fahrzeugtyp;
    }

    public void setFahrzeugtyp(String fahrzeugtyp) {
        this.fahrzeugtyp = fahrzeugtyp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FahrzeugEntity that = (FahrzeugEntity) o;

        if (fahrzeugId != that.fahrzeugId) return false;
        if (fahrzeugtyp != null ? !fahrzeugtyp.equals(that.fahrzeugtyp) : that.fahrzeugtyp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fahrzeugId;
        result = 31 * result + (fahrzeugtyp != null ? fahrzeugtyp.hashCode() : 0);
        return result;
    }
}
