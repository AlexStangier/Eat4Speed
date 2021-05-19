package de.eat4speed.services;

import de.eat4speed.entities.Bestellzuordnung;
import de.eat4speed.entities.Favoritenliste_Gerichte;
import de.eat4speed.repositories.BestellzuordnungRepository;
import de.eat4speed.repositories.Favoritenliste_GerichteRepository;
import de.eat4speed.services.interfaces.IBestellzuordnungService;
import de.eat4speed.services.interfaces.IFavoritenliste_GerichteService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class Favoritenliste_GerichteService implements IFavoritenliste_GerichteService {

    private Favoritenliste_GerichteRepository favoritenliste_gerichteRepository;

    @Inject
    public Favoritenliste_GerichteService(Favoritenliste_GerichteRepository favoritenliste_gerichteRepository) {
        this.favoritenliste_gerichteRepository = favoritenliste_gerichteRepository;
    }

    @Override
    public Response addFavoritenliste_Gerichte(Favoritenliste_Gerichte favoritenliste_gerichte)
    {
        favoritenliste_gerichteRepository.addFavoritenliste_Gerichte(favoritenliste_gerichte);

        return Response.status(Response.Status.CREATED).entity(favoritenliste_gerichte).build();
    }

    @Override
    public Response removeFavoritenliste_Gerichte(int gericht_id, int kundennummer)
    {
        favoritenliste_gerichteRepository.removeFavoritenliste_Gerichte(gericht_id, kundennummer);

        return Response.status(Response.Status.OK).build();
    }

}
