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

}
