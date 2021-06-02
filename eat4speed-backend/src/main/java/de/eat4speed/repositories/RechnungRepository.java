package de.eat4speed.repositories;


import de.eat4speed.entities.Benutzer;
import de.eat4speed.entities.Rechnung;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class RechnungRepository implements PanacheRepository<Rechnung> {

    @Transactional
    public Rechnung getRechnungByID(int id) { return find("Rechnungs_ID", id).firstResult(); }

}