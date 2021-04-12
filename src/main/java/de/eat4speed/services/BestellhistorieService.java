package de.eat4speed.services;

import de.eat4speed.repositories.BestellhistorieRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class BestellhistorieService {

    private BestellhistorieRepository bestellhistorieRepository;

    @Inject
    public BestellhistorieService(BestellhistorieRepository bestellhistorieRepository) {
        this.bestellhistorieRepository = bestellhistorieRepository;
    }
}
