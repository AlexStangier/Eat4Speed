package de.eat4speed.repositories;

import de.eat4speed.entities.Gericht;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class GerichtRepository implements PanacheRepository<Gericht> {

    public void addGericht(Gericht gericht)
    {
        persist(gericht);
    }

}