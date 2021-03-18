package de.eat4speed.repositories;

import de.eat4speed.entities.Benutzer;
import de.eat4speed.entities.Favoritenliste_Gerichte;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class Favoritenliste_GerichteRepository implements PanacheRepository<Favoritenliste_Gerichte> {


    @Transactional
    public void addFavoritenliste_Gerichte(Favoritenliste_Gerichte favoritenliste_gerichte)
    {
        persist(favoritenliste_gerichte);
    }

}
