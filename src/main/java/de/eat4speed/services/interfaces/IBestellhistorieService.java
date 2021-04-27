package de.eat4speed.services.interfaces;

import de.eat4speed.entities.Bestellhistorie;
import de.eat4speed.entities.Gericht;

import javax.ws.rs.core.Response;
import java.util.List;

public interface IBestellhistorieService {

    Response addBestellhistorie(Bestellhistorie bestellhistorie);

    Response deleteBestellhistorie(int id);
}
