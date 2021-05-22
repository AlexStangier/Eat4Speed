package de.eat4speed.services;

import de.eat4speed.entities.Bestellzuordnung;
import de.eat4speed.entities.Favoritenliste_Restaurants;
import de.eat4speed.repositories.BestellzuordnungRepository;
import de.eat4speed.repositories.Favoritenliste_RestaurantsRepository;
import de.eat4speed.services.interfaces.IBestellzuordnungService;
import de.eat4speed.services.interfaces.IFavoritenliste_RestaurantsService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class Favoritenliste_RestaurantsService implements IFavoritenliste_RestaurantsService {

    private Favoritenliste_RestaurantsRepository favoritenliste_restaurantsRepository;

    @Inject
    public Favoritenliste_RestaurantsService(Favoritenliste_RestaurantsRepository favoritenliste_restaurantsRepository) {
        this.favoritenliste_restaurantsRepository = favoritenliste_restaurantsRepository;
    }

    @Override
    public Response addFavoritenliste_Restaurants(Favoritenliste_Restaurants favoritenliste_restaurants)
    {
        favoritenliste_restaurantsRepository.addFavoritenliste_Restaurants(favoritenliste_restaurants);

        return Response.status(Response.Status.CREATED).entity(favoritenliste_restaurants).build();
    }

}
