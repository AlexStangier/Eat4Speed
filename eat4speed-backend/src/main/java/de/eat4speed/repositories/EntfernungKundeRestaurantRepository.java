package de.eat4speed.repositories;

import de.eat4speed.entities.EntfernungKundeRestaurant;
import de.eat4speed.entities.Kunde;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class EntfernungKundeRestaurantRepository implements PanacheRepository<EntfernungKundeRestaurant> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void addEntfernungKundeRestaurant(EntfernungKundeRestaurant entfernungKundeRestaurant)
    {
        persist(entfernungKundeRestaurant);
    }

    @Transactional
    public List getEntfernungByKundennummerRestaurant_ID(int kundennummer, int restaurant_ID)
    {
        List entfernungData;

        Query query = entityManager.createQuery(
                "SELECT ekr.entfernung " +
                        "FROM Kunde k, Restaurant r, EntfernungKundeRestaurant ekr " +
                        "WHERE k.kundennummer = ekr.kundennummer " +
                        "AND r.restaurant_ID = ekr.restaurant_ID " +
                        "AND k.kundennummer = ?1 " +
                        "AND r.restaurant_ID = ?2"
        ).setParameter(1,kundennummer).setParameter(2,restaurant_ID);

        entfernungData = query.getResultList();

        return entfernungData;
    }

}