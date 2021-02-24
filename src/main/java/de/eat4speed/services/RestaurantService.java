package de.eat4speed.services;

import de.eat4speed.entities.Restaurant;
import de.eat4speed.repositories.RestaurantRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class RestaurantService {

    private RestaurantRepository _restaurant;

    @Inject
    public RestaurantService(RestaurantRepository restaurant) {
        this._restaurant = restaurant;
    }

}
