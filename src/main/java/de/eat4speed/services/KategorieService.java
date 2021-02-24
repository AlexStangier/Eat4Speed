package de.eat4speed.services;

import de.eat4speed.entities.Kategorie;
import de.eat4speed.repositories.KategorieRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class KategorieService {

    private KategorieRepository _kategorie;

    @Inject
    public KategorieService(KategorieRepository kategorie) {
        this._kategorie = kategorie;
    }

}
