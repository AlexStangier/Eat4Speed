package de.eat4speed.repositories;

import de.eat4speed.entities.Fahrer;
import de.eat4speed.entities.Urlaub;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class UrlaubRepository implements PanacheRepository<Urlaub> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void addUrlaub(Urlaub urlaub)
    {
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=0;").executeUpdate();
        persist(urlaub);
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=1;").executeUpdate();

    }

    @Transactional
    public int istFahrer_im_Urlaub(int fahrernummer)
    {
        LocalDate today = LocalDate.now();

        Query query = entityManager.createNativeQuery("Select * From eatforspeed.Urlaub Where Anfang <= (\""
                + today + "\") And Ende >= (\"" + today + "\")");

        return (int)query.getResultStream().count();
    }

}