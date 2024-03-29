package de.eat4speed.services.interfaces;

import de.eat4speed.entities.Oeffnungszeiten;

import javax.ws.rs.core.Response;
import java.util.List;

public interface IOeffnungszeitenService {

    Response setArbeitstag(Oeffnungszeiten zeit);

    Response updateArbeitstag(Oeffnungszeiten zeit);

    List getAllZeiten(int restaurant_ID);

}