package de.eat4speed.services;

import de.eat4speed.repositories.AuftragRepository;
import de.eat4speed.repositories.BenutzerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AuftragService {

    private AuftragRepository auftragRepository;

    @Inject
    public AuftragService(AuftragRepository auftragRepository) {
        this.auftragRepository = auftragRepository;
    }

}
