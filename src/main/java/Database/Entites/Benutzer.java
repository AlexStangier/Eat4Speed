package Database.Entites;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Benutzer extends PanacheEntityBase {
    @Id @GeneratedValue Long Id;
    String Benutzername;
    String Email;
    String Password;
    String PayPal;
    int Rolle;


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public String getBenutzername() {
        return Benutzername;
    }

    public void setBenutzername(String benutzername) {
        Benutzername = benutzername;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPayPal() {
        return PayPal;
    }

    public void setPayPal(String payPal) {
        PayPal = payPal;
    }

    public int getRolle() {
        return Rolle;
    }

    public void setRolle(int rolle) {
        Rolle = rolle;
    }
}
