package de.eat4speed.services.interfaces;

import de.eat4speed.entities.Allergene;

import javax.ws.rs.core.Response;
import java.util.List;

public interface IAllergeneService {

    Response addAllergene(Allergene obj);

    List listAll();
}
