package de.eat4speed.services;

import de.eat4speed.entities.Kunde;
import de.eat4speed.repositories.KundeRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class KundeService {

    private KundeRepository _kunde;

    @Inject
    public KundeService(KundeRepository kunde) {
        this._kunde = kunde;
    }

}
