package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Benutzer extends PanacheEntityBase implements Serializable {

    @Id
    private String benutzername;
    private String E_Mail_Addresse;
    private String passwort;
    private String rolle;
    private String paypal_Account;

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getE_Mail_Addresse() {
        return E_Mail_Addresse;
    }

    public void setE_Mail_Addresse(String e_Mail_Addresse) {
        this.E_Mail_Addresse = e_Mail_Addresse;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getRolle() {
        return rolle;
    }

    public void setRolle(String rolle) {
        this.rolle = rolle;
    }

    public String getPaypal_Account() {
        return paypal_Account;
    }

    public void setPaypal_Account(String paypal_Account) {
        this.paypal_Account = paypal_Account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Benutzer that = (Benutzer) o;

        if (benutzername != null ? !benutzername.equals(that.benutzername) : that.benutzername != null) return false;
        if (E_Mail_Addresse != null ? !E_Mail_Addresse.equals(that.E_Mail_Addresse) : that.E_Mail_Addresse != null)
            return false;
        if (passwort != null ? !passwort.equals(that.passwort) : that.passwort != null) return false;
        if (rolle != null ? !rolle.equals(that.rolle) : that.rolle != null) return false;
        if (paypal_Account != null ? !paypal_Account.equals(that.paypal_Account) : that.paypal_Account != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = benutzername != null ? benutzername.hashCode() : 0;
        result = 31 * result + (E_Mail_Addresse != null ? E_Mail_Addresse.hashCode() : 0);
        result = 31 * result + (passwort != null ? passwort.hashCode() : 0);
        result = 31 * result + (rolle != null ? rolle.hashCode() : 0);
        result = 31 * result + (paypal_Account != null ? paypal_Account.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Benutzer{" +
                "benutzername='" + benutzername + '\'' +
                ", E_Mail_Addresse='" + E_Mail_Addresse + '\'' +
                ", passwort='" + passwort + '\'' +
                ", rolle='" + rolle + '\'' +
                ", paypal_Account='" + paypal_Account + '\'' +
                '}';
    }
}
