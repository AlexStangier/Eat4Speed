package de.eat4speed.repositories;

import de.eat4speed.entities.EntfernungKundeRestaurant;
import de.eat4speed.entities.Kunde;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class EntfernungKundeRestaurantRepository implements PanacheRepository<EntfernungKundeRestaurant> {

    @Transactional
    public void addEntfernungKundeRestaurant(EntfernungKundeRestaurant entfernungKundeRestaurant)
    {
        persist(entfernungKundeRestaurant);
    }

}