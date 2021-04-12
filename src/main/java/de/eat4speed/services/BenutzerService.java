package de.eat4speed.services;

import de.eat4speed.entities.Benutzer;
import de.eat4speed.repositories.BenutzerRepository;
import de.eat4speed.services.interfaces.IBenutzerService;
import org.wildfly.security.auth.realm.jdbc.mapper.PasswordKeyMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sound.sampled.AudioFormat;
import javax.transaction.Transactional;
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
}
