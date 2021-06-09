package de.eat4speed.services;

import de.eat4speed.entities.Gericht;
import de.eat4speed.entities.Oeffnungszeiten;
import de.eat4speed.repositories.GerichtRepository;
import de.eat4speed.repositories.OeffnungszeitenRepository;
import de.eat4speed.services.interfaces.IOeffnungszeitenService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class OeffnungszeitenService implements IOeffnungszeitenService{

    private OeffnungszeitenRepository oeffnungszeitenRepository;

    @Inject
    public OeffnungszeitenService(OeffnungszeitenRepository zeit) {
        this.oeffnungszeitenRepository = zeit;
    }

    @Override
    public Response setArbeitstag(Oeffnungszeiten zeit)
    {
        oeffnungszeitenRepository.setArbeitstag(zeit);

        return Response.status(Response.Status.CREATED).entity(zeit).build();
    }

    @Override
    public Response updateArbeitstag(Oeffnungszeiten zeit)
    {
        oeffnungszeitenRepository.updateArbeitstag(zeit);

        return Response.status(Response.Status.OK).entity(zeit).build();
    }

    @Override
    public List getAllZeiten(int restaurant_ID)
    {
        return oeffnungszeitenRepository.getAllZeiten(restaurant_ID);
    }
}
