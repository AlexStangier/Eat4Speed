package de.eat4speed.services.interfaces;

import de.eat4speed.entities.Schicht;

import javax.ws.rs.core.Response;
import java.util.List;

public interface ISchichtService {

    Response addSchicht(Schicht obj);

    List<String> getAllSchichtFromID(int fahrernummer);

    Schicht getSchichtHeute(int fahrernummer);

    Response deleteSchicht(int schicht_ID);
}
