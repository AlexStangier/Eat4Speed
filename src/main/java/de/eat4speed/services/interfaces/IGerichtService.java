package de.eat4speed.services.interfaces;

import de.eat4speed.entities.Gericht;

import javax.ws.rs.core.Response;
import java.util.List;

public interface IGerichtService {

    Response addGericht(Gericht gericht);

    List getAllGerichteDataRestaurantSpeisekarte(int restaurant_ID);
}
