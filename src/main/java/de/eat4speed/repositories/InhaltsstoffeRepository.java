package de.eat4speed.repositories;


import de.eat4speed.entities.Inhaltsstoffe;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class InhaltsstoffeRepository implements PanacheRepository<Inhaltsstoffe> {

    @Transactional
    public void addInhaltsstoffe(Inhaltsstoffe inhaltsstoffe)
    {
        persist(inhaltsstoffe);
    }

}