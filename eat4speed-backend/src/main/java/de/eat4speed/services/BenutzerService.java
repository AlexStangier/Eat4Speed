package de.eat4speed.services;

import de.eat4speed.dto.UserEmailDto;
import de.eat4speed.entities.Benutzer;
import de.eat4speed.repositories.BenutzerRepository;
import de.eat4speed.services.interfaces.IBenutzerService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Locale;

@ApplicationScoped
public class BenutzerService implements IBenutzerService {

    private BenutzerRepository _benutzer;

    @Inject
    public BenutzerService(BenutzerRepository benutzer) {
        this._benutzer = benutzer;
    }

    @Override
    public Response addBenutzer(Benutzer benutzer) {
        if (benutzer == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // check if received user contains necessary fields
        if (isAnyFieldEmpty(benutzer)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (isRoleInvalid(benutzer)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // Check if a user with that email address already exists
        String email = benutzer.getEmailAdresse();
        Integer userId = _benutzer.getBenutzerIdByEmail(new UserEmailDto(email));
        if (userId != -1) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // encode password in base64
        benutzer.setPasswort(Base64.getEncoder().encodeToString(benutzer.getPasswort().getBytes(StandardCharsets.UTF_8)));
        _benutzer.addBenutzer(benutzer);
        return Response.status(Response.Status.CREATED).entity(benutzer).build();
    }

    @Override
    public String listAll() {
        return _benutzer.listAll().toString();
    }

    /**
     * Comapares a given user with existing users in the database
     *
     * @param requestedUser
     * @return Benutzer if exists
     */
    @Override
    public Response checkCredentials(Benutzer requestedUser) {
        Benutzer currBenutzer = requestedUser.findMatchingEntryByEmail();
        //currBenutzer actually exists
        if (currBenutzer != null) {
            //Credentials are correct
            if (currBenutzer.getPasswort().equals(requestedUser.getPasswort())) {
                return Response.ok(currBenutzer, MediaType.APPLICATION_JSON).build();
                //Credentails doesn't match
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Benutzer Daten sind falsch!").build();
            }
        } else {
            //currBenutzer is null
            return Response.status(Response.Status.NOT_FOUND).entity("Benutzer existiert nicht!").build();
        }
    }

    @Override
    public Integer getEmailById(UserEmailDto email) {
        return _benutzer.getBenutzerIdByEmail(email);
    }

    @Override
    public List getBenutzerByLogin(String email) {
        return _benutzer.getBenutzerByLogin(email);
    }

    @Override
    public Response updateBenutzerRestaurant(Benutzer benutzer) {
        _benutzer.updateBenutzerRestaurant(benutzer);
        return Response.status(Response.Status.OK).entity(benutzer).build();
    }

    @Override
    public List getBenutzerKundeEinstellungenByLogin(String email) {
        return _benutzer.getBenutzerKundeEinstellungenByLogin(email);
    }

    @Override
    public List getKundennummerByBenutzername(String username) {
        return _benutzer.getKundennummerByBenutzername(username);
    }

    @Override
    public List getRestaurant_IDByBenutzername(String username) {
        return _benutzer.getRestaurant_IDByBenutzername(username);
    }

    private boolean isAnyFieldEmpty(Benutzer benutzer) {
        return benutzer.getBenutzername().isEmpty()
                || benutzer.getPasswort().isEmpty()
                || benutzer.getEmailAdresse().isEmpty()
                || benutzer.getVorname().isEmpty()
                || benutzer.getNachname().isEmpty()
                || benutzer.getRolle().isEmpty();
    }

    private boolean isRoleInvalid(Benutzer benutzer) {
        for (Benutzer.UserRole role : Benutzer.UserRole.values()) {
            if (role.toString().equals(benutzer.getRolle())) {
                return false;
            }
        }
        return true;
    }
}

