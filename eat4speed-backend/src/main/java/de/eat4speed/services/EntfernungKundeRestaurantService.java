package de.eat4speed.services;

import de.eat4speed.entities.EntfernungKundeRestaurant;
import de.eat4speed.repositories.EntfernungKundeRestaurantRepository;
import de.eat4speed.services.interfaces.IEntfernungKundeRestaurantService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
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

    @Override
    public List getEntfernungByKundennummerRestaurant_ID(int kundennummer, int restaurant_ID) {
        return entfernungKundeRestaurantRepository.getEntfernungByKundennummerRestaurant_ID(kundennummer, restaurant_ID);
    }

    @Override
    public Response deleteEntfernungByKundennummer(int kundennummer) {
        entfernungKundeRestaurantRepository.deleteEntfernungByKundennummer(kundennummer);
        return Response.status(Response.Status.OK).build();
    }

    @Override
    public Response deleteEntfernungByRestaurant_ID(int restaurant_ID) {
        entfernungKundeRestaurantRepository.deleteEntfernungByRestaurant_ID(restaurant_ID);
        return Response.status(Response.Status.OK).build();
    }


}
