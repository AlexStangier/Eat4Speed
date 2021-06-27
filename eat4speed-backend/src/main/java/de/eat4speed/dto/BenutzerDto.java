package de.eat4speed.dto;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BenutzerDto {
    public String benutzername;
    public String vorname;
    public String nachname;
    public String emailAdresse;
    public String passwort;
    public String rolle;
    public String paypal_Account;
    public int telefonnummer;

    public BenutzerDto() {
    }

    public BenutzerDto(String benutzername, String vorname, String nachname, String emailAdresse, String passwort, String rolle, String paypal_Account, int telefonnummer) {
        this.benutzername = benutzername;
        this.vorname = vorname;
        this.nachname = nachname;
        this.emailAdresse = emailAdresse;
        this.passwort = Base64.getEncoder().encodeToString(passwort.getBytes(StandardCharsets.UTF_8));
        this.rolle = rolle;
        this.paypal_Account = paypal_Account;
        this.telefonnummer = telefonnummer;
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
}
