package de.eat4speed.repositories;

import de.eat4speed.entities.Allergene;
import de.eat4speed.entities.Auftrag;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AllergeneRepository implements PanacheRepository<Allergene> {



}
