package de.eat4speed.repositories;

import de.eat4speed.entities.Urlaub;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class UrlaubRepository implements PanacheRepository<Urlaub> {



}