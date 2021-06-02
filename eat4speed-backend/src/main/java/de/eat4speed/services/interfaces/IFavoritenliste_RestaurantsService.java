package de.eat4speed.services.interfaces;

import de.eat4speed.entities.Bestellung;
import de.eat4speed.entities.Favoritenliste_Restaurants;

import javax.ws.rs.core.Response;

public interface IFavoritenliste_RestaurantsService {

    Response addFavoritenliste_Restaurants(Favoritenliste_Restaurants favoritenliste_restaurants);

    Response removeFavoritenliste_Restaurants(int restaurant_ID, int kundennummer);
}
