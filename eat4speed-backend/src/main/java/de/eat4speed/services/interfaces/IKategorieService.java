package de.eat4speed.services.interfaces;

import de.eat4speed.dto.CategoriePreferences;
import de.eat4speed.entities.Kategorie;

import javax.ws.rs.core.Response;
import java.util.List;

public interface IKategorieService {

    Response addKategorie(Kategorie obj);

    List listAll();

    CategoriePreferences getFavoriteCategorie(int customerId);
}
