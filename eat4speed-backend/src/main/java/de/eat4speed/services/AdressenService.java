package de.eat4speed.services;

import de.eat4speed.entities.Adressen;
import de.eat4speed.repositories.AdressenRepository;
import de.eat4speed.services.interfaces.IAdressenService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Locale;

@ApplicationScoped
public class AdressenService implements IAdressenService {

    private AdressenRepository _adressen;

    @Inject
    public AdressenService(AdressenRepository adressenRepository) {
        this._adressen = adressenRepository;
    }

    @Override
    public Response updateAdresse(Adressen adressen) {
        _adressen.updateAdresse(adressen);
        return Response.status(Response.Status.OK).entity(adressen).build();
    }

    @Override
    public List getAllKundeLngLat() {
        return _adressen.getAllKundeLngLat();
    }

    @Override
    public List getAllRestaurantLngLat() {
        return _adressen.getAllRestaurantLngLat();
    }


}
