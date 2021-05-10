package de.eat4speed.repositories;

import de.eat4speed.entities.Gericht;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class GerichtRepository implements PanacheRepository<Gericht> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void addGericht(Gericht gericht)
    {
        persist(gericht);
    }

    @Transactional
    public Gericht getGerichtByGerichtID(int gericht_ID)
    {
        return find("gericht_ID", gericht_ID).firstResult();
    }

    @Transactional
    public List getAllGerichteDataRestaurantSpeiseKarte(int restaurant_ID)
    {
        List allGerichteData;

        Query query = entityManager.createQuery(
                "SELECT g.gericht_ID, g.name, g.beschreibung, g.preis, g.gericht_ID " +
                        "FROM Gericht g " +
                        "WHERE g.restaurant_ID = ?1 " +
                        "AND g.ist_Getraenk = 0"
        ).setParameter(1,restaurant_ID);

        allGerichteData = query.getResultList();

        return allGerichteData;
    }

    @Transactional
    public List getAllGetraenkeDataRestaurantSpeiseKarte(int restaurant_ID)
    {
        List allGerichteData;

        Query query = entityManager.createQuery(
                "SELECT g.gericht_ID, g.name, g.beschreibung, g.preis, g.gericht_ID " +
                        "FROM Gericht g " +
                        "WHERE g.restaurant_ID = ?1 " +
                        "AND g.ist_Getraenk = 1"
        ).setParameter(1,restaurant_ID);

        allGerichteData = query.getResultList();

        return allGerichteData;
    }

    @Transactional
    public List getGerichtDataByGerichtName(String gerichtName)
    {
        List gerichteData;

        String likeName = "%"+gerichtName+"%";

        Query query = entityManager.createQuery(
                "SELECT g.gericht_ID, g.name, g.beschreibung, g.preis, g.verfuegbar, r.restaurant_ID, r.name_des_Restaurants, r.mindestbestellwert " +
                        "FROM Gericht g, Restaurant r " +
                        "WHERE g.restaurant_ID = r.restaurant_ID " +
                        "AND g.name LIKE ?1"
        ).setParameter(1,likeName);

        gerichteData = query.getResultList();

        return gerichteData;
    }

    @Transactional
    public List<Integer> getGericht_IDsByGerichtName(String gerichtName)
    {
        List<Integer> gerichteData;

        String likeName = "%"+gerichtName+"%";

        Query query = entityManager.createQuery(
                "SELECT g.gericht_ID " +
                        "FROM Gericht g " +
                        "WHERE g.name LIKE ?1"
        ).setParameter(1,likeName);

        gerichteData = query.getResultList();

        return gerichteData;
    }

    @Transactional
    public List<Integer> getGericht_IDsByGerichtKategorie(String kategorie)
    {
        List<Integer> gerichteData;

        String likeKategorie = "%"+kategorie+"%";

        Query query = entityManager.createQuery(
                "SELECT g.gericht_ID " +
                        "FROM Gericht g, Gericht_Kategorie kg " +
                        "WHERE g.gericht_ID = kg.gericht_ID " +
                        "AND kg.kategorie LIKE ?1"
        ).setParameter(1,likeKategorie);

        gerichteData = query.getResultList();

        return gerichteData;
    }

    @Transactional
    public List getGerichtDataByGericht_ID(int gericht_ID)
    {
        List gerichteData;

        Query query = entityManager.createQuery(
                "SELECT g.gericht_ID, g.name, g.beschreibung, g.preis, g.verfuegbar, r.restaurant_ID, r.name_des_Restaurants, r.mindestbestellwert " +
                        "FROM Gericht g, Restaurant r " +
                        "WHERE g.restaurant_ID = r.restaurant_ID " +
                        "AND g.gericht_ID = ?1"
        ).setParameter(1,gericht_ID);

        gerichteData = query.getResultList();

        return gerichteData;
    }

    @Transactional
    public List getGerichtDataByGerichtKategorie(String kategorie)
    {
        List gerichteData;

        String likeKategorie = "%"+kategorie+"%";

        Query query = entityManager.createQuery(
                "SELECT g.gericht_ID, g.name, g.beschreibung, g.preis, g.verfuegbar " +
                        "FROM Gericht g, Gericht_Kategorie kg " +
                        "WHERE g.gericht_ID = kg.gericht_ID " +
                        "AND kg.kategorie LIKE ?1"
        ).setParameter(1,likeKategorie);

        gerichteData = query.getResultList();

        return gerichteData;
    }

    @Transactional
    public List getGerichtDataByRestaurant_ID(int restaurant_ID)
    {
        List gerichteData;

        Query query = entityManager.createQuery(
                "SELECT g.gericht_ID, g.name, g.beschreibung, g.preis, g.verfuegbar " +
                        "FROM Gericht g " +
                        "WHERE g.restaurant_ID = ?1 "
        ).setParameter(1,restaurant_ID);

        gerichteData = query.getResultList();

        return gerichteData;
    }

    @Transactional
    public List getGerichtDataByKundennummer_Favoriten(int kundennummer)
    {
        List gerichteData;

        Query query = entityManager.createQuery(
                "SELECT g.gericht_ID, g.name, g.beschreibung, g.preis, g.verfuegbar, g.restaurant_ID, fg.anzahl_Bestellungen, fg.hinzufuegedatum " +
                        "FROM Gericht g, Kunde k, Favoritenliste_Gerichte fg " +
                        "WHERE g.gericht_ID = fg.gericht_ID " +
                        "AND k.kundennummer = fg.kundennummer " +
                        "AND k.kundennummer = ?1"
        ).setParameter(1,kundennummer);

        gerichteData = query.getResultList();

        return gerichteData;
    }

    @Transactional
    public List getGerichtDataByKundennummer_Bestellhistorie(int kundennummer)
    {
        List gerichteData;

        Query query = entityManager.createQuery(
                "SELECT g.gericht_ID, g.name, g.beschreibung, g.preis, g.verfuegbar " +
                        "FROM Gericht g, Bestellung b, Kunde k, Bestellzuordnung bz " +
                        "WHERE b.bestell_ID = bz.bestell_ID " +
                        "AND bz.gericht_ID = g.gericht_ID " +
                        "AND k.kundennummer = ?1"
        ).setParameter(1,kundennummer);

        gerichteData = query.getResultList();

        return gerichteData;
    }

    @Transactional
    public void updateGerichtAllData(Gericht gericht)
    {
        update("beschreibung = ?1, name = ?2, preis = ?3, verfuegbar = ?4 where gericht_ID = ?5", gericht.getBeschreibung(),gericht.getName(),gericht.getPreis(),gericht.getVerfuegbar(),gericht.getGericht_ID());
    }

    @Transactional
    public void updatePicturePath(String path, int id) {
        update("abbildung = ?1 where gericht_ID = ?2", path, id);
    }

    @Transactional
    public void deleteGericht(int id)
    {
        delete("gericht_ID",id);
    }
}