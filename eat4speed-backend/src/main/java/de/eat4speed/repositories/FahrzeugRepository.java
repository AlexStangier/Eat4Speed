package de.eat4speed.repositories;

import de.eat4speed.entities.Fahrzeug;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class FahrzeugRepository implements PanacheRepository<Fahrzeug> {

    @Transactional
    public void addFahrzeug(Fahrzeug fahrzeug)
    {
        persist(fahrzeug);
    }

    @Transactional
    public Fahrzeug findByFahrzeugID(int FahrzeugID)
    {
        return find("Fahrzeug_ID", FahrzeugID).firstResult();
    }
}
