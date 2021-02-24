package de.eat4speed.services;

import de.eat4speed.repositories.BenutzerRepository;
import de.eat4speed.repositories.FahrerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class FahrerService {

    private FahrerRepository fahrerRepository;

    @Inject
    public FahrerService(FahrerRepository fahrerRepository) {
        this.fahrerRepository = fahrerRepository;
    }

}
