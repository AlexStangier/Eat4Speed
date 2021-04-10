package de.eat4speed.controllers;


import de.eat4speed.repositories.Gericht_InhaltsstoffeRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/Gericht_Inhalststoffe")
public class Gericht_InhaltsstoffeController {


    @Inject
    Gericht_InhaltsstoffeRepository gericht_inhaltsstoffeRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return gericht_inhaltsstoffeRepository.listAll().toString();
    }

}
