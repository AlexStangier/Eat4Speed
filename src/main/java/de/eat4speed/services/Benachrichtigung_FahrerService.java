package de.eat4speed.services;

import de.eat4speed.repositories.Benachrichtigung_FahrerRepository;
import de.eat4speed.repositories.BenutzerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class Benachrichtigung_FahrerService {

    private Benachrichtigung_FahrerRepository benachrichtigung_fahrerRepository;

    @Inject
    public Benachrichtigung_FahrerService(Benachrichtigung_FahrerRepository benachrichtigung_fahrerRepository) {
        this.benachrichtigung_fahrerRepository = benachrichtigung_fahrerRepository;
    }

}
