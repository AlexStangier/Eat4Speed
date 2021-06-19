package de.eat4speed.repositories;

import de.eat4speed.entities.Bewertung;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class BewertungRepository implements PanacheRepository<Bewertung> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void addBewertung(Bewertung bewertung) {
        persist(bewertung);
    }

    @Transactional
    public void updateBewertung(Bewertung bewertung) {
        entityManager.createQuery(
                "UPDATE Bewertung " +
                        "SET sterne = ?1, text = ?2, datum = ?3 " +
                        "WHERE kundennummer = ?4 " +
                        "AND restaurant_ID = ?5"
        ).setParameter(1, bewertung.getSterne()).setParameter(2, bewertung.getText()).setParameter(3, bewertung.getDatum()).setParameter(4, bewertung.getKundennummer()).setParameter(5, bewertung.getRestaurant_ID()).executeUpdate();
    }

    @Transactional
    public List getBewertungDataByKundennummerAndRestaurant_ID(int kundennummer,int restaurant_ID)
    {
        List bewertungData;

        Query query = entityManager.createQuery(
                "SELECT b.bewertung_ID, b.kundennummer, b.restaurant_ID, b.sterne, b.text, b.datum, bn.benutzername " +
                        "FROM Bewertung b, Kunde k, Benutzer bn " +
                        "WHERE b.kundennummer = k.kundennummer " +
                        "AND k.benutzer_ID = bn.benutzer_ID " +
                        "AND b.kundennummer = ?1 " +
                        "AND b.restaurant_ID = ?2"
        ).setParameter(1,kundennummer).setParameter(2,restaurant_ID);

        bewertungData = query.getResultList();

        return bewertungData;
    }

    @Transactional
    public List getBewertungDataByRestaurant_ID(int restaurant_ID)
    {
        List bewertungData;

        Query query = entityManager.createQuery(
                "SELECT b.bewertung_ID, b.kundennummer, b.restaurant_ID, b.sterne, b.text, b.datum, bn.benutzername " +
                        "FROM Bewertung b, Kunde k, Benutzer bn " +
                        "WHERE b.kundennummer = k.kundennummer " +
                        "AND k.benutzer_ID = bn.benutzer_ID " +
                        "AND b.restaurant_ID = ?1 "
        ).setParameter(1,restaurant_ID);

        bewertungData = query.getResultList();

        return bewertungData;
    }

    @Transactional
    public List getAverageBewertungAndCountBewertungAllRestaurants()
    {
        List bewertungData;

        Query query = entityManager.createQuery(
                "SELECT AVG(b.sterne), count(b.sterne), r.restaurant_ID  " +
                        "FROM Bewertung b, Restaurant r " +
                        "WHERE b.restaurant_ID = r.restaurant_ID " +
                        "GROUP BY r.restaurant_ID"
        );

        bewertungData = query.getResultList();

        return bewertungData;
    }

    @Transactional
    public List getAverageBewertungAndCountBewertungByRestaurant_ID(int restaurant_ID)
    {
        List bewertungData;

        Query query = entityManager.createQuery(
                "SELECT AVG(b.sterne), count(b.sterne) " +
                        "FROM Bewertung b " +
                        "WHERE b.restaurant_ID = ?1 "
        ).setParameter(1,restaurant_ID);

        bewertungData = query.getResultList();

        return bewertungData;
    }

}
