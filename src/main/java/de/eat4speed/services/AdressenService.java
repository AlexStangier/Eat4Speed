package de.eat4speed.services;

import de.eat4speed.entities.Adressen;
import de.eat4speed.repositories.AdressenRepository;
import de.eat4speed.repositories.BenutzerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AdressenService {

    private AdressenRepository adressenRepository;

    @Inject
    public AdressenService(AdressenRepository adressenRepository) {
        this.adressenRepository = adressenRepository;
    }

    public Adressen getAdresseByID(Integer ID) { return this.adressenRepository.getAdresseByID(ID); }

}
