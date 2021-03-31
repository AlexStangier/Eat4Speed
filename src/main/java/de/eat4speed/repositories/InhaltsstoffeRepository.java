package de.eat4speed.repositories;


import de.eat4speed.entities.Inhaltsstoffe;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class InhaltsstoffeRepository implements PanacheRepository<Inhaltsstoffe> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void addInhaltsstoffe(Inhaltsstoffe inhaltsstoffe)
    {
        persist(inhaltsstoffe);
    }

    @Transactional
    public List listAllInhaltsstoffnamen()
    {
        List allInhaltsstoffeNamen;

        Query query = entityManager.createQuery(
                "SELECT i.name " +
                        "FROM Inhaltsstoffe i"
        );

        allInhaltsstoffeNamen = query.getResultList();

        return allInhaltsstoffeNamen;
    }
}