package de.eat4speed.services;

import de.eat4speed.repositories.BenutzerRepository;
import de.eat4speed.repositories.BestellungRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class BestellungService {

    private BestellungRepository bestellungRepository;

    @Inject
    public BestellungService(BestellungRepository bestellungRepository) {
        this.bestellungRepository = bestellungRepository;
    }

}
