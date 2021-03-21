package de.eat4speed.repositories;

import de.eat4speed.entities.Restaurant;
import de.eat4speed.entities.Status;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class RestaurantRepository implements PanacheRepository<Restaurant> {

    @Transactional
    public void addRestaurant(Restaurant restaurant) {
        persist(restaurant);
    }

}