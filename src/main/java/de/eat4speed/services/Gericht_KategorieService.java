package de.eat4speed.services;


import de.eat4speed.entities.Gericht_Kategorie;
import de.eat4speed.repositories.Gericht_KategorieRepository;
import de.eat4speed.services.interfaces.IGericht_KategorieService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class Gericht_KategorieService implements IGericht_KategorieService {


    private Gericht_KategorieRepository gericht_kategorieRepository;

    @Inject
    public Gericht_KategorieService(Gericht_KategorieRepository gericht_kategorieRepository) {
        this.gericht_kategorieRepository = gericht_kategorieRepository;
    }

    @Override
    public Response addGericht_Kategorie(Gericht_Kategorie gericht_kategorie) {
        gericht_kategorieRepository.addGericht_Kategorie(gericht_kategorie);

        return Response.status(Response.Status.CREATED).entity(gericht_kategorie).build();
    }

    @Override
    public List getGericht_KategorieByGericht_ID(int id)
    {
        return gericht_kategorieRepository.getGericht_KategorieByGericht_ID(id);
    }

    @Override
    public int deleteGerichtKategorieByGerichtID(int id)
    {
        return gericht_kategorieRepository.deleteGerichtKategorieByGerichtID(id);
    }


}
