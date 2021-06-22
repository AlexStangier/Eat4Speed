package de.eat4speed.repositories;

import de.eat4speed.entities.BenachrichtigungFahrerAuftrag;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

@ApplicationScoped
public class BenachrichtigungFahrerAuftragRepository implements PanacheRepository<BenachrichtigungFahrerAuftrag> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void addBenachrichtigungFahrerAuftrag(BenachrichtigungFahrerAuftrag benachrichtigung)
    {
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=0;").executeUpdate();
        entityManager.createNativeQuery("Insert INto eatforspeed.BenachrichtigungFahrerAuftrag\n" +
                "Values(" + benachrichtigung.getAuftrags_ID() + "," + benachrichtigung.getBenachrichtigungs_ID() +")").executeUpdate();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=1;").executeUpdate();

    }

    @Transactional
    public int deleteBenachrichtigungFahrerAuftrag(int Auftrags_ID, int Benachrichtigungs_ID)
    {
        entityManager.createNativeQuery("Delete from BenachrichtigungFahrerAuftrag where " +
                " auftrags_ID = "  + Auftrags_ID + " And Benachrichtigungs_ID = " + Benachrichtigungs_ID ).executeUpdate();

        return Auftrags_ID;
    }

}
