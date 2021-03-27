package de.eat4speed.services.interfaces;

import de.eat4speed.entities.Benutzer;

import javax.ws.rs.core.Response;
import java.util.List;

public interface IBenutzerService {

    //Todo change return to actual return type
    Response addBenutzer(Benutzer obj);

    String listAll();

}
