package de.eat4speed.repositories;

import de.eat4speed.entities.Bestellhistorie;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BestellhistorieRepository implements PanacheRepository<Bestellhistorie> {

}
