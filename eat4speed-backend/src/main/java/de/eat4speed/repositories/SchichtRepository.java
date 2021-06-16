package de.eat4speed.repositories;

import de.eat4speed.entities.Oeffnungszeiten;
import de.eat4speed.entities.Schicht;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class SchichtRepository implements PanacheRepository<Schicht> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void setSchicht(Schicht zeit)
    {
        persist(zeit);
    }

    @Transactional
    public void updateSchicht(Schicht zeit){
        update("anfang = ?1, ende = ?2 where fahrernummer = ?3", zeit.getAnfang(),zeit.getEnde(),zeit.getFahrernummer());
    }

    @Transactional
    public List getSchicht(int fahrerNummer)
    {
        List schicht;

        Query query = entityManager.createQuery(
                "SELECT t.anfang, t.ende, t.fahrernummer, t.schicht_ID " +
                        "FROM Schicht t " +
                        "WHERE t.fahrernummer = ?1 "
        ).setParameter(1,fahrerNummer);

        schicht = query.getResultList();

        return schicht;
    }
    public Object getAmountActiveSchicht()
    {

        Object amount;
        Query query = entityManager.createQuery(
                "SELECT count(s) " +
                        "FROM Schicht s " +
                        "WHERE CURRENT_TIME BETWEEN s.anfang AND s.ende"

        );
        amount = query.getSingleResult();
        return amount;
    }


}