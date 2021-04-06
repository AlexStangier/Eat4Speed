package de.eat4speed.repositories;

import de.eat4speed.entities.Gericht_Allergene;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class Gericht_AllergeneRepository implements PanacheRepository<Gericht_Allergene> {

    @Transactional
    public void addGericht_Allergene(Gericht_Allergene gericht_allergene)
    {
        persist(gericht_allergene);
    }

}