package de.eat4speed.services;

import de.eat4speed.entities.Benachrichtigung_Fahrer;
import de.eat4speed.repositories.Benachrichtigung_FahrerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class Benachrichtigung_FahrerService {

    private Benachrichtigung_FahrerRepository benachrichtigung_fahrerRepository;

    @Inject
    public Benachrichtigung_FahrerService(Benachrichtigung_FahrerRepository benachrichtigung_fahrerRepository) {
        this.benachrichtigung_fahrerRepository = benachrichtigung_fahrerRepository;
    }
    
    public Response addBenachrichtigung_Fahrer(Benachrichtigung_Fahrer obj)
    {
        benachrichtigung_fahrerRepository.addBenachrichtigung_Fahrer(obj);

        return Response.status(Response.Status.CREATED).entity(obj).build();
    }
}
