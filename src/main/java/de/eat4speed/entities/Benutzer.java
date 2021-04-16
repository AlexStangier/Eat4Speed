package de.eat4speed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Benutzer extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int benutzer_ID;
    private String benutzername;
    private String vorname;
    private String nachname;
    private String emailAdresse;
    private String passwort;
    private String rolle;
    private String paypal_Account;
    private int telefonnummer;

    public int getBenutzer_ID() {
        return benutzer_ID;
    }

    public void setBenutzer_ID(int benutzer_ID) {
        this.benutzer_ID = benutzer_ID;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
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

    public int getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(int telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Benutzer benutzer = (Benutzer) o;
        return benutzer_ID == benutzer.benutzer_ID && telefonnummer == benutzer.telefonnummer && Objects.equals(benutzername, benutzer.benutzername) && Objects.equals(vorname, benutzer.vorname) && Objects.equals(nachname, benutzer.nachname) && Objects.equals(emailAdresse, benutzer.emailAdresse) && Objects.equals(passwort, benutzer.passwort) && Objects.equals(rolle, benutzer.rolle) && Objects.equals(paypal_Account, benutzer.paypal_Account);
    }

    /**
     * Searches Benutzer table for entries matching the benutzername
     * @return Benutzer
     */
    public Benutzer findMatchingEntryByEmail(){
        return find("EmailAdress", emailAdresse).firstResult();
    }

    @Override
    public int hashCode() {
        return Objects.hash(benutzer_ID, benutzername, vorname, nachname, emailAdresse, passwort, rolle, paypal_Account, telefonnummer);
    }

    @Override
    public String toString() {
        return "Benutzer{" +
                "benutzer_ID=" + benutzer_ID +
                ", benutzername='" + benutzername + '\'' +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", emailAdresse='" + emailAdresse + '\'' +
                ", passwort='" + passwort + '\'' +
                ", rolle='" + rolle + '\'' +
                ", paypal_Account='" + paypal_Account + '\'' +
                ", telefonnummer=" + telefonnummer +
                '}';
    }
}
