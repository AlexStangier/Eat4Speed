package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.json.JSONObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class BenachrichtigungFahrerAuftrag extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int Auftrags_ID;
    int Benachrichtigungs_ID;

    public int getAuftrags_ID() {
        return Auftrags_ID;
    }

    public void setAuftrags_ID(int auftrags_ID) {
        Auftrags_ID = auftrags_ID;
    }

    public int getBenachrichtigungs_ID() {
        return Benachrichtigungs_ID;
    }

    public void setBenachrichtigungs_ID(int benachrichtigungs_ID) {
        Benachrichtigungs_ID = benachrichtigungs_ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        de.eat4speed.entities.BenachrichtigungFahrerAuftrag that = (BenachrichtigungFahrerAuftrag) o;

        if (Auftrags_ID != that.Auftrags_ID) return false;
        if (Benachrichtigungs_ID != that.Benachrichtigungs_ID) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Auftrags_ID;
        result = 31 * result + Benachrichtigungs_ID;
        return result;
    }

    @Override
    public String toString() {
        return "BenachrichtigungFahrerAuftrag{" +
                "Auftrags_ID=" + Auftrags_ID +
                ", Benachrichtigungs_ID=" + Benachrichtigungs_ID +
                '}';
    }

    public JSONObject toJSON()
    {
        return new JSONObject().put("Auftrags_ID", this.Auftrags_ID).put("Benachrichtigungs_ID", this.Benachrichtigungs_ID);
    }

    public static BenachrichtigungFahrerAuftrag fromJSON(String data)
    {
        JSONObject obj = new JSONObject(data);
        BenachrichtigungFahrerAuftrag b = new BenachrichtigungFahrerAuftrag();

        b.setAuftrags_ID(obj.getInt("Auftrags_ID"));
        b.setBenachrichtigungs_ID(obj.getInt("Benachrichtigungs_ID"));

        return b;
    }
}
