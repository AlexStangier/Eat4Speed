package de.eat4speed.services;

import de.eat4speed.entities.Oeffnungszeiten;
import de.eat4speed.entities.Schicht;
import de.eat4speed.repositories.OeffnungszeitenRepository;
import de.eat4speed.repositories.SchichtRepository;
import de.eat4speed.services.interfaces.IOeffnungszeitenService;
import de.eat4speed.services.interfaces.ISchichtService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class SchichtService implements ISchichtService {

    private SchichtRepository schichtRepository;

    @Inject
    public SchichtService(SchichtRepository zeit) {
        this.schichtRepository = zeit;
    }

    @Override
    public Response setSchicht(Schicht zeit)
    {
        schichtRepository.setSchicht(zeit);

        return Response.status(Response.Status.CREATED).entity(zeit).build();
    }

    @Override
    public Response updateSchicht(Schicht zeit)
    {
        schichtRepository.updateSchicht(zeit);

        return Response.status(Response.Status.OK).entity(zeit).build();
    }

    @Override
    public List getSchicht(int fahrer_ID) {
        return schichtRepository.getSchicht(fahrer_ID);
    }
}
