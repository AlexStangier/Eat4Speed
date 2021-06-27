package de.eat4speed.services;

import de.eat4speed.dto.BenutzerDto;
import de.eat4speed.dto.UserEmailDto;
import de.eat4speed.entities.Benutzer;
import de.eat4speed.repositories.BenutzerRepository;
import de.eat4speed.services.interfaces.IBenutzerService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class BenutzerService implements IBenutzerService {

    private BenutzerRepository _benutzer;

    @Inject
    public BenutzerService(BenutzerRepository benutzer) {
        this._benutzer = benutzer;
    }

    @Override
    public Response addBenutzer(BenutzerDto benutzer) {
        try {
            Benutzer user = new Benutzer(benutzer);
            _benutzer.addBenutzer(user);
            return Response.status(Response.Status.CREATED).entity(user).build();
        } catch (Exception e) {
            System.out.println(e);
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
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
    public Response checkCredentials(Benutzer requestedUser, char type) {
        Benutzer currBenutzer = requestedUser.findMatchingEntryByEmail();
        //currBenutzer actually exists

        if (currBenutzer != null) {
            //Credentials are correct
            if (currBenutzer.getPasswort().equals(requestedUser.getPasswort())) {
                switch (type) {
                    case 'a':
                        if (currBenutzer.getRolle().toLowerCase().equals("admin"))
                            return Response.ok(currBenutzer, MediaType.APPLICATION_JSON).build();
                        break;
                    case 'u':
                        if (currBenutzer.getRolle().toLowerCase().equals("kunde"))
                            return Response.ok(currBenutzer, MediaType.APPLICATION_JSON).build();
                        break;
                    case 'r':
                        if (currBenutzer.getRolle().toLowerCase().equals("restaurant"))
                            return Response.ok(currBenutzer, MediaType.APPLICATION_JSON).build();
                        break;
                    case 'd':
                        if (currBenutzer.getRolle().toLowerCase().equals("fahrer"))
                            return Response.ok(currBenutzer, MediaType.APPLICATION_JSON).build();
                        break;
                    default:
                        return Response.status(Response.Status.NOT_FOUND).entity("Benutzer Daten sind falsch!").build();
                }
                //Credentails don't match
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Benutzer Daten sind falsch!").build();
            }
        } else {
            //currBenutzer is null
            return Response.status(Response.Status.NOT_FOUND).entity("Benutzer existiert nicht!").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Benutzer existiert nicht!").build();
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

    @Override
    public String getRoleById(long id) {
        try {
            return _benutzer.getRoleById(id);
        } catch (Exception e) {
            return "";
        }

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

