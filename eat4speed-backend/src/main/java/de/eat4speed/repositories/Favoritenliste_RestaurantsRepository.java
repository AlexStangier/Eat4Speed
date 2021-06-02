package de.eat4speed.repositories;

import de.eat4speed.entities.Benutzer;
import de.eat4speed.entities.Favoritenliste_Restaurants;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class Favoritenliste_RestaurantsRepository implements PanacheRepository<Favoritenliste_Restaurants> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void addFavoritenliste_Restaurants(Favoritenliste_Restaurants favoritenliste_restaurants)
    {
        persist(favoritenliste_restaurants);
    }

    @Transactional
    public void removeFavoritenliste_Restaurants(int restaurant_ID, int kundennummer)
    {
        entityManager.createQuery(
                "DELETE from Favoritenliste_Restaurants " +
                        "WHERE restaurant_ID = ?1 " +
                        "AND kundennummer = ?2"
        ).setParameter(1,restaurant_ID).setParameter(2,kundennummer).executeUpdate();
    }

}
