package de.eat4speed.services.interfaces;

import de.eat4speed.entities.EntfernungKundeRestaurant;
import de.eat4speed.entities.Fahrer;
import de.eat4speed.entities.Fahrzeug;

import javax.ws.rs.core.Response;
import java.util.List;

public interface IEntfernungKundeRestaurantService {

    public Response addEntfernungKundeRestaurant(EntfernungKundeRestaurant entfernungKundeRestaurant);
}
