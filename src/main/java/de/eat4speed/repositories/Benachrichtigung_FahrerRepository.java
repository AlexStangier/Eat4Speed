package de.eat4speed.repositories;

import de.eat4speed.entities.Benachrichtigung_Fahrer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

@ApplicationScoped
public class Benachrichtigung_FahrerRepository implements PanacheRepository<Benachrichtigung_Fahrer> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void addBenachrichtigung_Fahrer(Benachrichtigung_Fahrer benachrichtigung)
    {
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=0;").executeUpdate();
        persist(benachrichtigung);
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=1;").executeUpdate();
    }

    @Transactional
    public int findIDByNachricht(String benachrichtigung, int fahrernummer)
    {
        Query query = entityManager.createNativeQuery(
                "Select Benachrichtigungs_ID FROM eatforspeed.Benachrichtigung_Fahrer WHERE " +
                        "Fahrernummer = " + fahrernummer +
                        " And Benachrichtigung = \"" + benachrichtigung + "\"");

        return (int)query.getResultList().get(0);
    }

    @Transactional
    public int deleteBenachrichtigungFahrer(int Benachrichtigungs_ID)
    {
        delete("Benachrichtigungs_ID", Benachrichtigungs_ID);

        return Benachrichtigungs_ID;
    }

}
