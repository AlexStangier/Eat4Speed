package de.eat4speed.services;

import de.eat4speed.entities.Rechnung;
import de.eat4speed.repositories.RechnungRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class RechnungService {

    private RechnungRepository _rechnung;

    @Inject
    public RechnungService(RechnungRepository rechnung) {
        this._rechnung = rechnung;
    }

}
