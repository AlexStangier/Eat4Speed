package de.eat4speed.services;

import de.eat4speed.entities.Bestellung;
import de.eat4speed.repositories.BenutzerRepository;
import de.eat4speed.repositories.BestellungRepository;
import de.eat4speed.services.interfaces.IBestellungService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class BestellungService implements IBestellungService {

    private BestellungRepository bestellungRepository;

    @Inject
    public BestellungService(BestellungRepository bestellungRepository) {
        this.bestellungRepository = bestellungRepository;
    }

    @Override
    public Response addBestellung(Bestellung bestellung)
    {
        bestellungRepository.addBestellung(bestellung);

        return Response.status(Response.Status.CREATED).entity(bestellung).build();
    }

}
