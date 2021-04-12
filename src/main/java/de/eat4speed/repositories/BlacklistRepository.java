package de.eat4speed.repositories;

import de.eat4speed.entities.Benutzer;
import de.eat4speed.entities.Blacklist;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class BlacklistRepository implements PanacheRepository<Blacklist> {


    @Transactional
    public void addBlacklist(Blacklist blacklist)
    {
        persist(blacklist);
    }

}
