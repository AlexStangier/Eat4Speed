package de.eat4speed.repositories;

import de.eat4speed.entities.Allergene;
import de.eat4speed.entities.Auftrag;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class AllergeneRepository implements PanacheRepository<Allergene> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void addAllergene(Allergene allergene)
    {
        persist(allergene);
    }

    @Transactional
    public List listAllAllergeneNamen()
    {
        List allAllergeneNamen;

        Query query = entityManager.createQuery(
                "SELECT a.name " +
                        "FROM Allergene a "
        );

        allAllergeneNamen = query.getResultList();

        return allAllergeneNamen;


    }

}
