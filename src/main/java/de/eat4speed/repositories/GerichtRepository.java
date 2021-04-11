package de.eat4speed.repositories;

import de.eat4speed.entities.Gericht;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class GerichtRepository implements PanacheRepository<Gericht> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void addGericht(Gericht gericht)
    {
        persist(gericht);
    }

    @Transactional
    public Gericht getGerichtByGerichtID(int gericht_ID)
    {
        return find("gerichtId", gericht_ID).firstResult();
    }

    @Transactional
    public List getAllGerichteDataRestaurantSpeiseKarte(int restaurant_ID)
    {
        List allGerichteData;

        Query query = entityManager.createQuery(
                "SELECT g.gericht_ID, g.name, g.beschreibung, g.preis, g.gericht_ID " +
                        "FROM Gericht g " +
                        "WHERE g.restaurant_ID = ?1"
        ).setParameter(1,restaurant_ID);

        allGerichteData = query.getResultList();

        return allGerichteData;
    }

    @Transactional
    public void updateGerichtAllData(Gericht gericht)
    {
        update("beschreibung = ?1, name = ?2, preis = ?3, verfuegbar = ?4 where gerichtId = ?5", gericht.getBeschreibung(),gericht.getName(),gericht.getPreis(),gericht.getVerfuegbar(),gericht.getGericht_ID());
    }

}