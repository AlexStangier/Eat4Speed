package de.eat4speed.services;

import de.eat4speed.repositories.BenutzerRepository;
import de.eat4speed.repositories.EinnahmenRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EinnahmenService {

    private EinnahmenRepository einnahmenRepository;

    @Inject
    public EinnahmenService(EinnahmenRepository einnahmenRepository) {
        this.einnahmenRepository = einnahmenRepository;
    }

}
