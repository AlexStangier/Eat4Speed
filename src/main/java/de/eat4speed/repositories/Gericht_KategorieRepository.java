package de.eat4speed.repositories;


import de.eat4speed.entities.Gericht_Kategorie;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class Gericht_KategorieRepository implements PanacheRepository<Gericht_Kategorie> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void addGericht_Kategorie(Gericht_Kategorie gericht_kategorie)
    {
        persist(gericht_kategorie);
    }

    @Transactional
    public List getGericht_KategorieByGericht_ID(int gericht_ID)
    {
        List kategorieByGericht_ID;

        Query query = entityManager.createQuery("" +
                "SELECT ka.kategorie " +
                "FROM Gericht_Kategorie ka " +
                "WHERE ka.gericht_ID = ?1"
        ).setParameter(1,gericht_ID);

        kategorieByGericht_ID = query.getResultList();

        return kategorieByGericht_ID;
    }

    @Transactional
    public int deleteGerichtKategorieByGerichtID(int id)
    {
        delete("Gericht_ID",id);

        return id;
    }
}