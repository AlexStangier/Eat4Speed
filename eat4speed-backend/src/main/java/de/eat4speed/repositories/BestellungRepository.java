package de.eat4speed.repositories;

import de.eat4speed.entities.Adressen;
import de.eat4speed.entities.Benutzer;
import de.eat4speed.entities.Bestellung;
import de.eat4speed.entities.Gericht;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class BestellungRepository implements PanacheRepository<Bestellung> {

    @Transactional
    public void addBestellung(Bestellung bestellung)
    {
        persist(bestellung);
    }

    @Transactional
    public Bestellung getBestellungByID(int id) {
        return find("Bestell_ID", id).firstResult();
    }

    @Transactional
    public Bestellung getBestellungByAuftragsId(int id) {
        return find("Auftrags_ID", id).firstResult();
    }


}
