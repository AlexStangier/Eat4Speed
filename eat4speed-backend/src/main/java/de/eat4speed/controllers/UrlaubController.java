package de.eat4speed.controllers;


import de.eat4speed.entities.Urlaub;
import de.eat4speed.repositories.UrlaubRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/Urlaub")
public class UrlaubController {


    @Inject
    UrlaubRepository urlaubRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return urlaubRepository.listAll().toString();
    }

    @GET
    @Path("/HeuteUrlaub/{fahrernummer}")
    @Produces(MediaType.TEXT_PLAIN)
    public int getHeuteUrlaub(@PathParam("fahrernummer") int fahrernummer){
        return urlaubRepository.istFahrer_im_Urlaub(fahrernummer);
    }

}
