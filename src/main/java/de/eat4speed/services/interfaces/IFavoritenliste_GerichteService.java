package de.eat4speed.services.interfaces;

import de.eat4speed.entities.Favoritenliste_Gerichte;
import de.eat4speed.entities.Favoritenliste_Restaurants;

import javax.ws.rs.core.Response;

public interface IFavoritenliste_GerichteService {

    Response addFavoritenliste_Gerichte(Favoritenliste_Gerichte favoritenliste_gerichte);

}
