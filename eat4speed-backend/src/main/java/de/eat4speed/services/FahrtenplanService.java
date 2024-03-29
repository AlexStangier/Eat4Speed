package de.eat4speed.services;

import de.eat4speed.entities.Fahrtenplan_Station;
import de.eat4speed.repositories.FahrtenplanRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class FahrtenplanService {

    private FahrtenplanRepository fahrtenplanRepository;

    @Inject
    public FahrtenplanService(FahrtenplanRepository fahrtenplanRepository) {
        this.fahrtenplanRepository = fahrtenplanRepository;
    }

    public Fahrtenplan_Station getFahrtenPlanStaion(int id)
    {
        return this.fahrtenplanRepository.findByStationsID(id);
    }
}
