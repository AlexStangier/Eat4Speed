package de.eat4speed.services;

import de.eat4speed.entities.Bewertung;
import de.eat4speed.repositories.BewertungRepository;
import de.eat4speed.services.interfaces.IBewertungService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class BewertungService implements IBewertungService {

    private BewertungRepository bewertungRepository;

    @Inject
    public BewertungService(BewertungRepository bewertungRepository) {
        this.bewertungRepository = bewertungRepository;
    }


    @Override
    public Response addOrUpdateBewertung(Bewertung bewertung) {

        List bewertungen = bewertungRepository.getBewertungDataByKundennummerAndRestaurant_ID(bewertung.getKundennummer(), bewertung.getRestaurant_ID());
        if(bewertungen.size()>0)
        {
            bewertungRepository.updateBewertung(bewertung);
        }
        else {
            bewertungRepository.addBewertung(bewertung);
        }
        return Response.status(Response.Status.CREATED).entity(bewertung).build();
    }

    @Override
    public List getBewertungDataByKundennummerAndRestaurant_ID(int kundennummer, int restaurant_ID) {
        return bewertungRepository.getBewertungDataByKundennummerAndRestaurant_ID(kundennummer, restaurant_ID);
    }

    @Override
    public List getBewertungDataByRestaurant_ID(int restaurant_ID) {
        return bewertungRepository.getBewertungDataByRestaurant_ID(restaurant_ID);
    }

    @Override
    public List getAverageBewertungAndCountBewertungByRestaurant_ID(int restaurant_ID) {
        return bewertungRepository.getAverageBewertungAndCountBewertungByRestaurant_ID(restaurant_ID);
    }
}
