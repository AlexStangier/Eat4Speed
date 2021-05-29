package de.eat4speed.repositories;

import de.eat4speed.entities.Auftrag;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class AuftragRepository implements PanacheRepository<Auftrag> {

    @Transactional
    public void addAuftrag(Auftrag auftrag) {
        persist(auftrag);
    }

    @Transactional
    public void deleteAuftag(int id) {
        delete("auftrags_ID",id);
    }

    @Transactional
    public void updateAuftragStatus(int id, String status)
    {
        update("status = ?1 where id = ?2",status,id);
    }

}
