package de.eat4speed.services;

import de.eat4speed.entities.Oeffnungszeiten;
import de.eat4speed.entities.Restaurant;
import de.eat4speed.repositories.OeffnungszeitenRepository;
import de.eat4speed.repositories.RestaurantRepository;
import de.eat4speed.services.interfaces.IOeffnungszeitenService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class OeffnungszeitenService implements IOeffnungszeitenService{

    private final OeffnungszeitenRepository oeffnungszeitenRepository;

    @Inject
    RestaurantRepository _restaurantRepository;

    @Inject
    public OeffnungszeitenService(OeffnungszeitenRepository zeit) {
        this.oeffnungszeitenRepository = zeit;
    }

    @Override
    public Response setArbeitstag(Oeffnungszeiten zeit) {
        if (areAnyPropertiesInvalid(zeit)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        oeffnungszeitenRepository.setArbeitstag(zeit);
        return Response.status(Response.Status.CREATED).entity(zeit).build();
    }

    @Override
    public Response updateArbeitstag(Oeffnungszeiten zeit) {
        if (areAnyPropertiesInvalid(zeit)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        oeffnungszeitenRepository.updateArbeitstag(zeit);
        return Response.status(Response.Status.OK).entity(zeit).build();
    }

    @Override
    public List getAllZeiten(int restaurant_ID) {
        return oeffnungszeitenRepository.getAllZeiten(restaurant_ID);
    }

    private boolean areAnyPropertiesInvalid(Oeffnungszeiten openingHours) {
        if (openingHours == null) {
            return true;
        }

        if (openingHours.getAnfang().after(openingHours.getEnde())) {
            return true;
        }

        if (isWeekdayInvalid(openingHours)) {
            return true;
        }

        // Does a restaurant with this id exist?
        Restaurant restaurant = this._restaurantRepository.findByRestaurantnummer(openingHours.getRestaurant_ID());
        if (restaurant == null) {
            return true;
        }

        return false;
    }

    private boolean isWeekdayInvalid(Oeffnungszeiten openingHours) {
        for (Oeffnungszeiten.DayOfWeek dayOfWeek : Oeffnungszeiten.DayOfWeek.values()) {
            if (dayOfWeek.value().equals(openingHours.getWochentag())) {
                return false;
            }
        }
        return true;
    }
}
