package de.eat4speed.services;

import de.eat4speed.repositories.BewertungRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class BewertungService {

    private BewertungRepository bewertungRepository;

    @Inject
    public BewertungService(BewertungRepository bewertungRepository) {
        this.bewertungRepository = bewertungRepository;
    }
}
