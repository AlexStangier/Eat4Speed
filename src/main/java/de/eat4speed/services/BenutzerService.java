package de.eat4speed.services;

import de.eat4speed.entities.BenutzerEntity;
import de.eat4speed.repositories.BenutzerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class BenutzerService {

    private BenutzerRepository _benutzer;

    @Inject
    public BenutzerService(BenutzerRepository benutzer) {
        this._benutzer = benutzer;
    }

    public BenutzerEntity getById(int id){
        return _benutzer.findById((long) id);
    }
}
