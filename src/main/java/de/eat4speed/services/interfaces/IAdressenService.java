package de.eat4speed.services.interfaces;

import de.eat4speed.entities.Adressen;


import javax.ws.rs.core.Response;
import java.util.List;

public interface IAdressenService {

    Response updateAdresse(Adressen adressen);
}