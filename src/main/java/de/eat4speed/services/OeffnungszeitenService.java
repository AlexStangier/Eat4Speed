package de.eat4speed.services;

import de.eat4speed.entities.Oeffnungszeiten;
import de.eat4speed.repositories.OeffnungszeitenRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class OeffnungszeitenService {

    private OeffnungszeitenRepository _oeffnungszeiten;

    @Inject
    public OeffnungszeitenService(OeffnungszeitenRepository oeffnungszeiten) {
        this._oeffnungszeiten = oeffnungszeiten;
    }

}
