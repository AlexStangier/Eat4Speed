package de.eat4speed.services;

import de.eat4speed.entities.Schicht;
import de.eat4speed.repositories.SchichtRepository;
import de.eat4speed.services.interfaces.ISchichtService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class SchichtService implements ISchichtService {

    private SchichtRepository schichtRepository;

    @Inject
    public SchichtService(SchichtRepository schichtRepository) { this.schichtRepository = schichtRepository; }

    @Override
    public Response addSchicht(Schicht schicht)
    {
        schichtRepository.addSchicht(schicht);

        return Response.status(Response.Status.CREATED).entity(schicht).build();
    }

    @Override
    public List getAllSchichtFromID(int fahrernummer) { return schichtRepository.getAllSchichtFromID(fahrernummer); }

    @Override
    public Schicht getSchichtHeute(int fahrernummer) { return schichtRepository.getSchichtHeute(fahrernummer); }

    @Override
    public Response deleteSchicht(int schicht_ID)
    {
        schichtRepository.deleteSchicht(schicht_ID);

        return Response.status(Response.Status.OK).build();
    }
}
