package de.eat4speed.services;

import de.eat4speed.repositories.Benachrichtigung_KundeRepository;
import de.eat4speed.repositories.BenutzerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class Benachrichtigung_KundeService {

    private Benachrichtigung_KundeRepository benachrichtigung_kundeRepository;

    @Inject
    public Benachrichtigung_KundeService(Benachrichtigung_KundeRepository benachrichtigung_kundeRepository) {
        this.benachrichtigung_kundeRepository = benachrichtigung_kundeRepository;
    }

}
