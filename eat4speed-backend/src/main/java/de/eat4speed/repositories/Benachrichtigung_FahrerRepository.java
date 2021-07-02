package de.eat4speed.repositories;

import de.eat4speed.entities.Adressen;
import de.eat4speed.entities.Benachrichtigung_Fahrer;
import de.eat4speed.entities.Restaurant;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class Benachrichtigung_FahrerRepository implements PanacheRepository<Benachrichtigung_Fahrer> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void markAsGelesen(int benachrichtigungs_ID)
    {
        update("gelesen = 1 where benachrichtigungs_ID = ?1", benachrichtigungs_ID);
    }

    @Transactional
    public List getAllBenachrichtigungFahrerUngelesen(int fahrernummer) {
        List allBenachrichtigungData;

        Query query = entityManager.createQuery(
                "SELECT bf.benachrichtigungs_ID, bf.auftrags_ID " +
                        "FROM Benachrichtigung_Fahrer bf " +
                        "WHERE bf.fahrernummer = ?1 " +
                        "AND bf.gelesen = 0 "

        ).setParameter(1,fahrernummer);
        allBenachrichtigungData = query.getResultList();

        return allBenachrichtigungData;
    }

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
