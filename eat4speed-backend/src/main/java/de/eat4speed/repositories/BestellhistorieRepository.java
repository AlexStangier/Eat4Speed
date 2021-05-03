package de.eat4speed.repositories;

import de.eat4speed.entities.Bestellhistorie;
import de.eat4speed.entities.Gericht;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class BestellhistorieRepository implements PanacheRepository<Bestellhistorie> {

    @Transactional
    public void addBestellhistorie(Bestellhistorie bestellhistorie)
    {
        persist(bestellhistorie);
    }

    @Transactional
    public void deleteBestellhistorie(int id)
    {
        delete("bestellhistorien_ID",id);
    }
}
