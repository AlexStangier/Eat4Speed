package de.eat4speed.services.interfaces;

import de.eat4speed.dto.UserEmailDto;
import de.eat4speed.entities.Benutzer;
import de.eat4speed.entities.Favoritenliste_Restaurants;

import javax.ws.rs.core.Response;
import java.util.List;

public interface IBenutzerService {

    Response addBenutzer(Benutzer obj);

    String listAll();

    /**
     * Comapares a given user with existing users in the database
     *
     * @param requestedUser
     * @return Benutzer if exists
     */
    Response checkCredentials(Benutzer requestedUser);

    Integer getEmailById(UserEmailDto email);

    List getBenutzerByLogin(String email);

    Response updateBenutzerRestaurant(Benutzer benutzer);

    List getBenutzerKundeEinstellungenByLogin(String email);
}