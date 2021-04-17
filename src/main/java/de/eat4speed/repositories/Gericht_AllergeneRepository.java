package de.eat4speed.repositories;

import de.eat4speed.entities.Gericht_Allergene;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class Gericht_AllergeneRepository implements PanacheRepository<Gericht_Allergene> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void addGericht_Allergene(Gericht_Allergene gericht_allergene)
    {
        persist(gericht_allergene);
    }

    @Transactional
    public List getGericht_AllergeneByGericht_ID(int gericht_ID)
    {
        List allergeneByGericht_ID;

        Query query = entityManager.createQuery("" +
                "SELECT ga.allergen " +
                "FROM Gericht_Allergene ga " +
                "WHERE ga.gericht_ID = ?1"
        ).setParameter(1,gericht_ID);

        allergeneByGericht_ID = query.getResultList();

        return allergeneByGericht_ID;
    }

    @Transactional
    public int deleteGerichtAllergeneByGericht_ID(int id)
    {
        delete("gericht_id",id);

        return id;
    }
}