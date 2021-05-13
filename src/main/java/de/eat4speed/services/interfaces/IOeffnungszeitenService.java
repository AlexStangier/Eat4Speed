package de.eat4speed.services.interfaces;

import de.eat4speed.dishAlternatives.DishAlternativesOptions;
import de.eat4speed.entities.Gericht;
import de.eat4speed.entities.Oeffnungszeiten;
import de.eat4speed.multipart.MultipartBody;

import javax.ws.rs.core.Response;
import java.util.List;

public interface IOeffnungszeitenService {

    Response setArbeitstag(Oeffnungszeiten zeit);

}