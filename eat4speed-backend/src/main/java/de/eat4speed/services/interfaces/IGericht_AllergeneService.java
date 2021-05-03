package de.eat4speed.services.interfaces;

import de.eat4speed.entities.Gericht_Allergene;

import javax.ws.rs.core.Response;
import java.util.List;

public interface IGericht_AllergeneService {

    Response addGericht_Allergene(Gericht_Allergene gericht_allergene);

    List getGericht_AllergeneByGericht_ID(int id);

    int deleteGerichtAllergeneByGerichtID(int id);
}
