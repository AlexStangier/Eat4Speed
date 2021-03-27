package de.eat4speed.repositories;

import de.eat4speed.entities.Benutzer;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class BenutzerRepository implements PanacheRepository<Benutzer> {

    @Transactional
    public void addBenutzer(Benutzer benutzer)
    {
        persist(benutzer);
    }

}
