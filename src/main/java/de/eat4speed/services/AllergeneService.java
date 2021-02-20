package de.eat4speed.services;

import de.eat4speed.repositories.AllergeneRepository;
import de.eat4speed.repositories.BenutzerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AllergeneService {

    private AllergeneRepository allergeneRepository;

    @Inject
    public AllergeneService(AllergeneRepository allergeneRepository) {
        this.allergeneRepository = allergeneRepository;
    }

}
