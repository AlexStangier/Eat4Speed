package de.eat4speed.services;

import de.eat4speed.dishAlternatives.DishAlternativesOptions;
import de.eat4speed.entities.EntfernungKundeRestaurant;
import de.eat4speed.entities.Gericht;
import de.eat4speed.repositories.EntfernungKundeRestaurantRepository;
import de.eat4speed.repositories.GerichtRepository;
import de.eat4speed.services.interfaces.IEntfernungKundeRestaurantService;
import de.eat4speed.services.interfaces.IGerichtService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class EntfernungKundeRestaurantService implements IEntfernungKundeRestaurantService {

    private EntfernungKundeRestaurantRepository entfernungKundeRestaurantRepository;

    @Inject
    public EntfernungKundeRestaurantService(EntfernungKundeRestaurantRepository entfernungKundeRestaurantRepository) {
        this.entfernungKundeRestaurantRepository = entfernungKundeRestaurantRepository;
    }


    @Override
    public Response addEntfernungKundeRestaurant(EntfernungKundeRestaurant entfernungKundeRestaurant) {
        entfernungKundeRestaurantRepository.addEntfernungKundeRestaurant(entfernungKundeRestaurant);

        return Response.status(Response.Status.CREATED).entity(entfernungKundeRestaurant).build();
    }
}
