package de.eat4speed.repositories;

import de.eat4speed.entities.Benutzer;
import de.eat4speed.entities.Favoritenliste_Gerichte;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class Favoritenliste_GerichteRepository implements PanacheRepository<Favoritenliste_Gerichte> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void addFavoritenliste_Gerichte(Favoritenliste_Gerichte favoritenliste_gerichte)
    {
        persist(favoritenliste_gerichte);
    }

    @Transactional
    public void removeFavoritenliste_Gerichte(int gericht_id, int kundennummer)
    {
        entityManager.createQuery(
                "DELETE from Favoritenliste_Gerichte " +
                        "WHERE gericht_ID = ?1 " +
                        "AND kundennummer = ?2"
        ).setParameter(1,gericht_id).setParameter(2,kundennummer).executeUpdate();
    }
}
