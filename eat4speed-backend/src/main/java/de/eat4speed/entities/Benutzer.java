package de.eat4speed.entities;

import de.eat4speed.dto.BenutzerDto;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Locale;
import java.util.Objects;

@Entity
public class Benutzer extends PanacheEntityBase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int benutzer_ID;
    private String benutzername;
    private String vorname;
    private String nachname;
    //private int Anschrift;
    private String emailAdresse;
    private String passwort;
    private String rolle;
    private String paypal_Account;
    private String telefonnummer;
    private byte geloescht;

    public Benutzer() {
    }

    public Benutzer(BenutzerDto dto) {
        this.benutzername = dto.getBenutzername();
        this.vorname = dto.getVorname();
        this.nachname = dto.getNachname();
        this.emailAdresse = dto.getEmailAdresse();
        this.passwort = Base64.getEncoder().encodeToString(dto.getPasswort().getBytes(StandardCharsets.UTF_8));
        this.rolle = dto.getRolle();
        this.paypal_Account = dto.getPaypal_Account();
        this.telefonnummer = dto.getTelefonnummer();
    }

    public Benutzer(String benutzername, String vorname, String nachname, String emailAdresse, String passwort, String rolle, String paypal_Account, String telefonnummer) {
        this.benutzername = benutzername;
        this.vorname = vorname;
        this.nachname = nachname;
        this.emailAdresse = emailAdresse;
        this.passwort = passwort;
        this.rolle = rolle;
        this.paypal_Account = paypal_Account;
        this.telefonnummer = telefonnummer;
    }

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

    public String getTelefonnummer() {
        return telefonnummer;
    }

    /*public int getAnschrift() {
        return Anschrift;
    }

    public void setAnschrift(int anschrift) {
        Anschrift = anschrift;
    }*/

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    /**
     * Searches Benutzer table for entries matching the benutzername
     *
     * @return Benutzer
     */
    public Benutzer findMatchingEntryByEmail() {
        return find("EmailAdresse", emailAdresse).firstResult();
    }

    public byte getGeloescht() {
        return geloescht;
    }

    public void setGeloescht(byte geloescht) {
        this.geloescht = geloescht;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Benutzer benutzer = (Benutzer) o;
        return benutzer_ID == benutzer.benutzer_ID && telefonnummer == benutzer.telefonnummer && geloescht == benutzer.geloescht && Objects.equals(benutzername, benutzer.benutzername) && Objects.equals(vorname, benutzer.vorname) && Objects.equals(nachname, benutzer.nachname) && Objects.equals(emailAdresse, benutzer.emailAdresse) && Objects.equals(passwort, benutzer.passwort) && Objects.equals(rolle, benutzer.rolle) && Objects.equals(paypal_Account, benutzer.paypal_Account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(benutzer_ID, benutzername, vorname, nachname, emailAdresse, passwort, rolle, paypal_Account, telefonnummer, geloescht);
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
                ", geloescht=" + geloescht +
                '}';
    }

    public enum UserRole {
        KUNDE,
        RESTAURANT,
        ADMIN,
        FAHRER;

        @Override
        public String toString() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}
