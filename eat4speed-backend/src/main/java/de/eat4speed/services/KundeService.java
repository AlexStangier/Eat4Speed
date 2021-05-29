package de.eat4speed.services;

import de.eat4speed.entities.Kunde;
import de.eat4speed.repositories.KundeRepository;
import de.eat4speed.services.interfaces.IKundeService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class KundeService implements IKundeService{

    private KundeRepository _kunde;

    @Inject
    public KundeService(KundeRepository kunde) {
        this._kunde = kunde;
    }

    @Override
    public Response updateKundeEinstellungen(Kunde kunde) {
        _kunde.updateKundeEinstellungen(kunde);
        return Response.status(Response.Status.OK).entity(kunde).build();
    }

}
