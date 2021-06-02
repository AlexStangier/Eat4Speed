package de.eat4speed.services;

import de.eat4speed.repositories.BenutzerRepository;
import de.eat4speed.repositories.FahrzeugRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class FahrzeugService {

    private FahrzeugRepository fahrzeugRepository;

    @Inject
    public FahrzeugService(FahrzeugRepository fahrzeugRepository) {
        this.fahrzeugRepository = fahrzeugRepository;
    }

}
