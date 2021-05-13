package de.eat4speed.repositories;

import de.eat4speed.entities.Schicht;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;

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
    public void addSchicht(Schicht schicht)
    {
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=0;").executeUpdate();
        persist(schicht);
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=1;").executeUpdate();
    }

    @Transactional
    public List getAllSchichtFromID(int fahrernummer)
    {
        List allSchichtData;

        Query query = entityManager.createQuery(
                "Select s.schicht_ID, s.fahrernummer, s.anfang, s.ende " +
                        "From Schicht s " +
                        "Where s.fahrernummer = " + fahrernummer
        );
        allSchichtData = query.getResultList();

        return allSchichtData;
    }

    @Transactional
    public Schicht getSchichtHeute(int fahrernummer)
    {
        return find("Fahrernummer", Sort.by("Ende"), fahrernummer ).firstResult();
    }

    @Transactional
    public int deleteSchicht(int schicht_ID)
    {
        delete("Schicht_ID", schicht_ID);

        return schicht_ID;
    }
}
