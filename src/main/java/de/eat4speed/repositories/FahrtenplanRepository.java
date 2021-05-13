package de.eat4speed.repositories;

import de.eat4speed.entities.Fahrtenplan_Station;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class FahrtenplanRepository implements PanacheRepository<Fahrtenplan_Station> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public Fahrtenplan_Station findByStationsID(int StationsID)
    {
        return find("Stations_ID", StationsID).firstResult();
    }
}