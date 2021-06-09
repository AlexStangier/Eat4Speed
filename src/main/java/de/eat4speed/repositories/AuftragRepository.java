package de.eat4speed.repositories;

import de.eat4speed.entities.Auftrag;

import de.eat4speed.entities.Fahrtenplan_Station;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class AuftragRepository implements PanacheRepository<Auftrag> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void addAuftrag(Auftrag auftrag)
    {
        persist(auftrag);
    }

    @Transactional
    public Auftrag getByID(int id)
    {
        return find("Auftrags_ID", id).firstResult();
    }


}
