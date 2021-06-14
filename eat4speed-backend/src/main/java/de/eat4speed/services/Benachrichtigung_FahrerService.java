package de.eat4speed.services;

import de.eat4speed.repositories.Benachrichtigung_FahrerRepository;
import de.eat4speed.services.interfaces.IBenachrichtigung_FahrerService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class Benachrichtigung_FahrerService implements IBenachrichtigung_FahrerService {

    private Benachrichtigung_FahrerRepository benachrichtigung_fahrerRepository;

    @Inject
    public Benachrichtigung_FahrerService(Benachrichtigung_FahrerRepository benachrichtigung_fahrerRepository) {
        this.benachrichtigung_fahrerRepository = benachrichtigung_fahrerRepository;
    }

    @Override
    public List getAllBenachrichtigung_Fahrer_ungelesen(int fahrernummer) {
        return benachrichtigung_fahrerRepository.getAllBenachrichtigungFahrerUngelesen(fahrernummer);
    }

    @Override
    public void markAsGelesen(int benachrichtigungs_ID) {
        benachrichtigung_fahrerRepository.markAsGelesen(benachrichtigungs_ID);
    }
}
