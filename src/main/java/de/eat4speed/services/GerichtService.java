package de.eat4speed.services;

import de.eat4speed.entities.Gericht;
import de.eat4speed.repositories.GerichtRepository;
import de.eat4speed.services.interfaces.IGerichtService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class GerichtService implements IGerichtService {

    private GerichtRepository gerichtRepository;

    @Inject
    public GerichtService(GerichtRepository gericht) {
        this.gerichtRepository = gericht;
    }

    @Override
    public Response addGericht(Gericht gericht)
    {
        gerichtRepository.addGericht(gericht);

        return Response.status(Response.Status.CREATED).entity(gericht).build();
    }

    @Override
    public List getAllGerichteDataRestaurantSpeisekarte(int restaurant_ID)
    {
        return gerichtRepository.getAllGerichteDataRestaurantSpeiseKarte(restaurant_ID);
    }

}
