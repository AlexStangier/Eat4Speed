package de.eat4speed.repositories;

import de.eat4speed.entities.Oeffnungszeiten;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class OeffnungszeitenRepository implements PanacheRepository<Oeffnungszeiten> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void setArbeitstag(Oeffnungszeiten zeit)
    {
        persist(zeit);
    }

    @Transactional
    public void updateArbeitstag(Oeffnungszeiten zeit)
    {
        update("anfang = ?1, ende = ?2 where wochentag = ?3 and restaurant_ID = ?4", zeit.getAnfang(),zeit.getEnde(),zeit.getWochentag(),zeit.getRestaurant_ID());
    }

    @Transactional
    public List getAllZeiten(int restaurant_ID)
    {
        List allZeiten;

        Query query = entityManager.createQuery(
                "SELECT t.anfang, t.ende, t.wochentag, t.oeffnungszeiten_ID " +
                        "FROM Oeffnungszeiten t " +
                        "WHERE t.restaurant_ID = ?1 "
        ).setParameter(1,restaurant_ID);

        allZeiten = query.getResultList();

        return allZeiten;
    }

    @Transactional
    public List getAllZeitenWochentag(int restaurant_ID, String wochentag)
    {
        List allZeiten;

        Query query = entityManager.createQuery(
                "SELECT t.anfang, t.ende " +
                        "FROM Oeffnungszeiten t " +
                        "WHERE t.restaurant_ID = ?1 " +
                        "AND t.wochentag LIKE ?2"
        ).setParameter(1,restaurant_ID).setParameter(2,wochentag);

        allZeiten = query.getResultList();

        return allZeiten;
    }

    @Transactional
    public List getAllZeitenRestaurant_ID(int restaurant_ID)
    {
        List allZeiten;

        Query query = entityManager.createQuery(
                "SELECT t.wochentag, t.anfang, t.ende " +
                        "FROM Oeffnungszeiten t " +
                        "WHERE t.restaurant_ID = ?1"
        ).setParameter(1,restaurant_ID);

        allZeiten = query.getResultList();

        return allZeiten;
    }

    @Transactional
    public Oeffnungszeiten getOeffnungszeitenById(int openingHoursId) {
        return find("Oeffnungszeiten_ID", openingHoursId).firstResult();
    }

    @Transactional
    public void deleteOeffnungszeitenById(int openingHoursId) {
        delete("Oeffnungszeiten_ID", openingHoursId);
    }
}