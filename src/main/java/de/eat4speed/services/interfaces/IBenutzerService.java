package de.eat4speed.services.interfaces;

import de.eat4speed.dto.UserEmailDto;
import de.eat4speed.entities.Benutzer;

import javax.ws.rs.core.Response;

public interface IBenutzerService {

    Response addBenutzer(Benutzer obj);

    String listAll();

    /**
     * Comapares a given user with existing users in the database
     * @return Benutzer if exists
     * @param requestedUser
     */
    Response checkCredentials(Benutzer requestedUser);

    Integer getEmailById(UserEmailDto email);
}
