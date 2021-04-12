package de.eat4speed.repositories;

import de.eat4speed.entities.Allergene;
import de.eat4speed.entities.Kategorie;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class KategorieRepository implements PanacheRepository<Kategorie> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void addKategorie(Kategorie kategorie)
    {
        persist(kategorie);
    }

    @Transactional
    public List listAllKategories()
    {
        List allKategorienNamen;

        Query query = entityManager.createQuery(
                "SELECT k.name " +
                        "FROM Kategorie k "
        );

        allKategorienNamen = query.getResultList();

        return allKategorienNamen;
    }

}