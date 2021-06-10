package de.eat4speed.repositories;

import de.eat4speed.entities.Oeffnungszeiten;
import de.eat4speed.entities.Schicht;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class SchichtRepository implements PanacheRepository<Schicht> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void setSchicht(Schicht zeit)
    {
        persist(zeit);
    }



    @Transactional
    public List getAllZeiten(int schicht_ID)
    {
        List allZeiten;

        Query query = entityManager.createQuery(
                "SELECT t.anfang, t.ende, t.fahrernummer, t.schicht_ID " +
                        "FROM Schicht t " +
                        "WHERE t.schicht_ID = ?1 "
        ).setParameter(1,schicht_ID);

        allZeiten = query.getResultList();

        return allZeiten;
    }

}