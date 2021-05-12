package de.eat4speed.repositories;

import de.eat4speed.entities.Gericht;
import de.eat4speed.entities.Oeffnungszeiten;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class OeffnungszeitenRepository implements PanacheRepository<Oeffnungszeiten> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void setArbeitstag(Oeffnungszeiten zeit)
    {
        persist(zeit);
    }

}