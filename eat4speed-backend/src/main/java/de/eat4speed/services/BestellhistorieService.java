package de.eat4speed.services;

import de.eat4speed.entities.Bestellhistorie;
import de.eat4speed.repositories.BestellhistorieRepository;
import de.eat4speed.services.interfaces.IBestellhistorieService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class BestellhistorieService implements IBestellhistorieService {

    private BestellhistorieRepository bestellhistorieRepository;

    @Inject
    public BestellhistorieService(BestellhistorieRepository bestellhistorieRepository) {
        this.bestellhistorieRepository = bestellhistorieRepository;
    }

    @Override
    public Response addBestellhistorie(Bestellhistorie bestellhistorie)
    {
        bestellhistorieRepository.addBestellhistorie(bestellhistorie);

        return Response.status(Response.Status.CREATED).entity(bestellhistorie).build();
    }

    @Override
    public Response deleteBestellhistorie(int id) {

        bestellhistorieRepository.deleteBestellhistorie(id);

        return Response.status(Response.Status.OK).build();
    }
}
