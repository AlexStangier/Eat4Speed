package de.eat4speed.services.interfaces;

import de.eat4speed.entities.Gericht_Kategorie;

import javax.ws.rs.core.Response;
import java.util.List;

public interface IGericht_KategorieService {

    Response addGericht_Kategorie(Gericht_Kategorie gericht_kategorie);

    List getGericht_KategorieByGericht_ID(int id);

    int deleteGerichtKategorieByGerichtID(int id);
}
