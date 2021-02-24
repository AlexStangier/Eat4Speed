package de.eat4speed.services;

import de.eat4speed.entities.Gericht;
import de.eat4speed.repositories.GerichtRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class GerichtService {

    private GerichtRepository _gericht;

    @Inject
    public GerichtService(GerichtRepository gericht) {
        this._gericht = gericht;
    }

}
