package de.eat4speed.repositories;

import de.eat4speed.entities.Adressen;
import de.eat4speed.entities.Bestellzuordnung;
import de.eat4speed.entities.Gericht;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class BestellzuordnungRepository implements PanacheRepository<Bestellzuordnung> {

    @Transactional
    public void addBestellzuordnung(Bestellzuordnung bestellzuordnung)
    {
        persist(bestellzuordnung);
    }

    @Transactional
    public void deleteBestellzuordnung(int id)
    {
        delete("gericht_ID",id);
    }

}
