package de.eat4speed.repositories;

import de.eat4speed.entities.Adressen;
import de.eat4speed.entities.Fahrer;
import de.eat4speed.entities.Kunde;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class FahrerRepository implements PanacheRepository<Fahrer> {

    @Transactional
    public void addFahrer(Fahrer fahrer)
    {
        persist(fahrer);
    }

    @Transactional
    public void updateFahrer_Fahrzeug_id(int fahrernummer, int fahrzeug_Id)
    {
        update("fahrzeug = ?1 where fahrernummer = ?2", fahrzeug_Id, fahrernummer);
    }

    public Fahrer findByFahrernummer(int fahrernummer)
    {
        return find("fahrernummer", fahrernummer).firstResult();
    }
}
