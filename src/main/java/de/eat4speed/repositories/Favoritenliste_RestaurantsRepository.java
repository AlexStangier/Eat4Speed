package de.eat4speed.repositories;

import de.eat4speed.entities.Benutzer;
import de.eat4speed.entities.Favoritenliste_Restaurants;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class Favoritenliste_RestaurantsRepository implements PanacheRepository<Favoritenliste_Restaurants> {


    @Transactional
    public void addFavoritenliste_Restaurants(Favoritenliste_Restaurants favoritenliste_restaurants)
    {
        persist(favoritenliste_restaurants);
    }

}
