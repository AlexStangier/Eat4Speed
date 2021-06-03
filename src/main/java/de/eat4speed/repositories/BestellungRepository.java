package de.eat4speed.repositories;

import de.eat4speed.entities.Bestellung;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.json.JSONArray;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

@ApplicationScoped
public class BestellungRepository implements PanacheRepository<Bestellung> {


    @Inject
    EntityManager entityManager;

    @Transactional
    public void addBestellung(Bestellung bestellung)
    {
        persist(bestellung);
    }

    @Transactional
    public Bestellung getBestellungByID(int id) {
        return find("Bestell_ID", id).firstResult();
    }

    public JSONArray getGerichteByAuftragID(int id)
    {
        Query query = entityManager.createNativeQuery("Select Gericht_IDs from eatforspeed.Bestellung " +
                "Where Auftrags_ID= " + id);

        return new JSONArray(query.getResultList());
    }

}
