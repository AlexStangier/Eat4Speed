package de.eat4speed.services;

import de.eat4speed.entities.Bestellzuordnung;
import de.eat4speed.repositories.BestellzuordnungRepository;
import de.eat4speed.services.interfaces.IBestellzuordnungService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class BestellzuordnungService implements IBestellzuordnungService {

    private BestellzuordnungRepository bestellzuordnungRepository;

    @Inject
    public BestellzuordnungService(BestellzuordnungRepository bestellzuordnungRepository) {
        this.bestellzuordnungRepository = bestellzuordnungRepository;
    }

    @Override
    public Response addBestellzuordnung(Bestellzuordnung bestellzuordnung)
    {
        bestellzuordnungRepository.addBestellzuordnung(bestellzuordnung);

        return Response.status(Response.Status.CREATED).entity(bestellzuordnung).build();
    }

    @Override
    public Response deleteBestellzuordnung(int id) {

        bestellzuordnungRepository.deleteBestellzuordnung(id);

        return Response.status(Response.Status.OK).build();
    }
}
