package de.eat4speed.services;

import de.eat4speed.entities.Gericht_Allergene;
import de.eat4speed.repositories.Gericht_AllergeneRepository;
import de.eat4speed.services.interfaces.IGericht_AllergeneService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class Gericht_AllergeneService implements IGericht_AllergeneService {

    private Gericht_AllergeneRepository gericht_allergeneRepository;

    @Inject
    public Gericht_AllergeneService(Gericht_AllergeneRepository gericht_allergeneRepository)
    {
        this.gericht_allergeneRepository = gericht_allergeneRepository;
    }

    @Override
    public Response addGericht_Allergene(Gericht_Allergene gericht_allergene)
    {
        gericht_allergeneRepository.addGericht_Allergene(gericht_allergene);

        return Response.status(Response.Status.CREATED).entity(gericht_allergene).build();
    }

    @Override
    public List getGericht_AllergeneByGericht_ID(int id)
    {
        return gericht_allergeneRepository.getGericht_AllergeneByGericht_ID(id);
    }

    @Override
    public int deleteGerichtAllergeneByGerichtID(int id)
    {
        return gericht_allergeneRepository.deleteGerichtAllergeneByGericht_ID(id);
    }

}
