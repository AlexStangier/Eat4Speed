package de.eat4speed.repositories;

import de.eat4speed.entities.Fahrtenplan_Station;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FahrtenplanRepository implements PanacheRepository<Fahrtenplan_Station> {

}
