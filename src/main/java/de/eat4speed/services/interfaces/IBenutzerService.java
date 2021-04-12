package de.eat4speed.services.interfaces;

import de.eat4speed.entities.Benutzer;

import javax.ws.rs.core.Response;
import java.util.List;

public interface IBenutzerService {

    Response addBenutzer(Benutzer obj);

    String listAll();

}
