package de.eat4speed.services;

import de.eat4speed.entities.Allergene;
import de.eat4speed.repositories.AllergeneRepository;
import de.eat4speed.services.interfaces.IAllergeneService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class AllergeneService implements IAllergeneService {

    private AllergeneRepository allergeneRepository;

    @Inject
    public AllergeneService(AllergeneRepository allergeneRepository) {
        this.allergeneRepository = allergeneRepository;
    }

    @Override
    public Response addAllergene(Allergene allergene)
    {
        allergeneRepository.addAllergene(allergene);

        return Response.status(Response.Status.CREATED).entity(allergene).build();
    }

    @Override
    public List listAll()
    {
        return allergeneRepository.listAllAllergeneNamen();
    }
}
