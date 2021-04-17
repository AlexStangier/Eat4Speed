package de.eat4speed.services;

import de.eat4speed.entities.Gericht;
import de.eat4speed.multipart.MultipartBody;
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
    public Gericht getGerichtByGerichtID(int gericht_ID)
    {
        return gerichtRepository.getGerichtByGerichtID(gericht_ID);
    }

    @Override
    public List getAllGerichteDataRestaurantSpeisekarte(int restaurant_ID)
    {
        return gerichtRepository.getAllGerichteDataRestaurantSpeiseKarte(restaurant_ID);
    }

    @Override
    public List getAllGetraenkeDataRestaurantSpeiseKarte(int restaurant_ID)
    {
        return gerichtRepository.getAllGetraenkeDataRestaurantSpeiseKarte(restaurant_ID);
    }

    @Override
    public Response updateGerichtAllData(Gericht gericht)
    {
        gerichtRepository.updateGerichtAllData(gericht);

        return Response.status(Response.Status.OK).entity(gericht).build();
    }

    @Override
    public void updatePicturePath(String path, int id)
    {
        gerichtRepository.updatePicturePath(path, id);
    }

    @Override
    public Response deleteGericht(int gericht_ID)
    {
        gerichtRepository.deleteGericht(gericht_ID);

        return Response.status(Response.Status.OK).build();
    }

}
