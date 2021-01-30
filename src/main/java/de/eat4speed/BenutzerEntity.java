package de.eat4speed;

import javax.persistence.*;

@Entity
@Table(name = "Benutzer", schema = "eatforspeed", catalog = "")
public class BenutzerEntity {
    private Long id;
    private String benutzername;
    private String eMailAddresse;
    private String passwort;
    private String rolle;
    private String paypalAccount;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "Benutzername")
    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    @Basic
    @Column(name = "E_Mail_Addresse")
    public String geteMailAddresse() {
        return eMailAddresse;
    }

    public void seteMailAddresse(String eMailAddresse) {
        this.eMailAddresse = eMailAddresse;
    }

    @Basic
    @Column(name = "Passwort")
    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    @Basic
    @Column(name = "Rolle")
    public String getRolle() {
        return rolle;
    }

    public void setRolle(String rolle) {
        this.rolle = rolle;
    }

    @Basic
    @Column(name = "Paypal_Account")
    public String getPaypalAccount() {
        return paypalAccount;
    }

    public void setPaypalAccount(String paypalAccount) {
        this.paypalAccount = paypalAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BenutzerEntity that = (BenutzerEntity) o;

        if (benutzername != null ? !benutzername.equals(that.benutzername) : that.benutzername != null) return false;
        if (eMailAddresse != null ? !eMailAddresse.equals(that.eMailAddresse) : that.eMailAddresse != null)
            return false;
        if (passwort != null ? !passwort.equals(that.passwort) : that.passwort != null) return false;
        if (rolle != null ? !rolle.equals(that.rolle) : that.rolle != null) return false;
        if (paypalAccount != null ? !paypalAccount.equals(that.paypalAccount) : that.paypalAccount != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = benutzername != null ? benutzername.hashCode() : 0;
        result = 31 * result + (eMailAddresse != null ? eMailAddresse.hashCode() : 0);
        result = 31 * result + (passwort != null ? passwort.hashCode() : 0);
        result = 31 * result + (rolle != null ? rolle.hashCode() : 0);
        result = 31 * result + (paypalAccount != null ? paypalAccount.hashCode() : 0);
        return result;
    }
}
