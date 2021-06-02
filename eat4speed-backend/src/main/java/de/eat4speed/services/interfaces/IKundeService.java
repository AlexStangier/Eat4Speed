package de.eat4speed.services.interfaces;

import de.eat4speed.entities.Kunde;


import javax.ws.rs.core.Response;
import java.util.List;

public interface IKundeService {

    Response updateKundeEinstellungen(Kunde kunde);
}