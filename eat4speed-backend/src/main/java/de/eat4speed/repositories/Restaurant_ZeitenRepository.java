package de.eat4speed.repositories;

import de.eat4speed.entities.Restaurant_Zeiten;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class Restaurant_ZeitenRepository implements PanacheRepository<Restaurant_Zeiten> {



}