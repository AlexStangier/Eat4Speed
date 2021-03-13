package Database.Manager;

import Database.Entites.Benutzer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

public class DatabaseUserService {
    EntityManager em;

    @Transactional
    public Benutzer createUser(String name, String email, String password, String paypal, int role) {
        Benutzer user = new Benutzer();
        long id = Benutzer.count();
        user.setId(id++);
        user.setBenutzername("Udo");
        user.setEmail("udo@udo.de");
        user.setPassword("123");
        user.setPayPal("udo@udo.de");
        user.setRolle(1);

        return user;
    }
}
