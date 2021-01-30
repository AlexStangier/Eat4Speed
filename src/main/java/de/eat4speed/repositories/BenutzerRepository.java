package de.eat4speed.repositories;

import de.eat4speed.entities.BenutzerEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BenutzerRepository implements PanacheRepository<BenutzerEntity> {
}
