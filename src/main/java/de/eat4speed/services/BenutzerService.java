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
import java.util.Locale;

@ApplicationScoped
public class BenutzerService implements IBenutzerService {

    private BenutzerRepository _benutzer;

    @Inject
    public BenutzerService(BenutzerRepository benutzer) {
        this._benutzer = benutzer;
    }

    @Override
    public Response addBenutzer(Benutzer obj) {
        //hash password to base64
        if (obj != null) {
            obj.setPasswort(Base64.getEncoder().encodeToString(obj.getPasswort().getBytes(StandardCharsets.UTF_8)));
            obj.setRolle(obj.getRolle().toLowerCase(Locale.ROOT));
            _benutzer.addBenutzer(obj);
        }
        return Response.status(Response.Status.CREATED).entity(obj).build();
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
}
