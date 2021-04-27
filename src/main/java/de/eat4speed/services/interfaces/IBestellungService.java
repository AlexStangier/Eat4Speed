package de.eat4speed.services.interfaces;

import de.eat4speed.entities.Auftrag;
import de.eat4speed.entities.Bestellung;

import javax.ws.rs.core.Response;

public interface IBestellungService {

    Response addBestellung(Bestellung bestellung);

}
