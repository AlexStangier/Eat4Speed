package de.eat4speed.services;

import de.eat4speed.entities.Benutzer;
import de.eat4speed.repositories.BenutzerRepository;
import de.eat4speed.services.interfaces.IBenutzerService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class BenutzerService implements IBenutzerService {

    /**
     * TODO: when adding new users use base64 to hash passwords
     */

    private BenutzerRepository _benutzer;

    @Inject
    public BenutzerService(BenutzerRepository benutzer) {
        this._benutzer = benutzer;
    }

    @Override
    public Response addBenutzer(Benutzer obj) {
        _benutzer.addBenutzer(obj);
        return Response.status(Response.Status.CREATED).entity(obj).build();
    }

    @Override
    public String listAll() {
        return _benutzer.listAll().toString();
    }
}
