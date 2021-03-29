package de.eat4speed.services;

import de.eat4speed.entities.Fahrer;
import de.eat4speed.entities.Fahrzeug;
import de.eat4speed.repositories.BenutzerRepository;
import de.eat4speed.repositories.FahrerRepository;
import de.eat4speed.services.interfaces.IFahrerService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class FahrerService implements IFahrerService {

    private FahrerRepository fahrerRepository;

    @Inject
    public FahrerService(FahrerRepository fahrerRepository) {
        this.fahrerRepository = fahrerRepository;
    }

    @Override
    public Response addFahrer(Fahrer fahrer)
    {
        fahrerRepository.addFahrer(fahrer);

        return Response.status(Response.Status.CREATED).entity(fahrer).build();
    }

    @Override
    public List getAllFahrer()
    {
        return fahrerRepository.getAllFahrer();
    }

    @Override
    public String getNotVerifiedFahrer()
    {
        return null;
    }

    @Override
    public String getVerifiedFahrer()
    {
        return null;
    }

    @Override
    public Response updateFahrer_Fahrzeug_Id(int id, Fahrzeug fahrzeug)
    {
        fahrerRepository.updateFahrer_Fahrzeug_id(id, fahrzeug.getFahrzeug_Id());

        return Response.status(Response.Status.OK).entity(fahrzeug).build();
    }

}
