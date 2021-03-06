package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Benutzer extends PanacheEntityBase implements Serializable {

    @Id
    private String benutzername;
    private String emailAdresse;
    private String passwort;
    private String rolle;
    private String paypal_Account;

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getEmailAdresse() {
        return emailAdresse;
    }

    public void setEmailAdresse(String emailAdresse) {
        this.emailAdresse = emailAdresse;
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
        if (emailAdresse != null ? !emailAdresse.equals(that.emailAdresse) : that.emailAdresse != null)
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
        result = 31 * result + (emailAdresse != null ? emailAdresse.hashCode() : 0);
        result = 31 * result + (passwort != null ? passwort.hashCode() : 0);
        result = 31 * result + (rolle != null ? rolle.hashCode() : 0);
        result = 31 * result + (paypal_Account != null ? paypal_Account.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Benutzer{" +
                "benutzername='" + benutzername + '\'' +
                ", emailAdresse='" + emailAdresse + '\'' +
                ", passwort='" + passwort + '\'' +
                ", rolle='" + rolle + '\'' +
                ", paypal_Account='" + paypal_Account + '\'' +
                '}';
    }
}
