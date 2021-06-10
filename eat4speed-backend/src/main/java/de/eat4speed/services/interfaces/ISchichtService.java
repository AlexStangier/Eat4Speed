package de.eat4speed.services.interfaces;

import de.eat4speed.entities.Schicht;

import javax.ws.rs.core.Response;
import java.util.List;

public interface ISchichtService {

    Response setSchicht(Schicht zeit);

    List getAllZeiten(int restaurant_ID);
}