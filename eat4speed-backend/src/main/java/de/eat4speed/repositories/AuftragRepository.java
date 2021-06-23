package de.eat4speed.repositories;

import de.eat4speed.entities.Auftrag;

import de.eat4speed.entities.Benutzer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class AuftragRepository implements PanacheRepository<Auftrag> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void addAuftrag(Auftrag auftrag) {
        persist(auftrag);
    }

    @Transactional
    public void deleteAuftag(int id) {
        delete("auftrags_ID", id);
    }

    @Transactional
    public void updateAuftragStatus(int id, String status) {
        update("status = ?1 where id = ?2", status, id);
    }

    @Transactional
    public void updateAuftragFahrernummer(int auftrags_ID, int fahrernummer) {
        update("fahrernummer = ?1 where auftrags_ID = ?2", fahrernummer, (long) auftrags_ID);
    }

    @Transactional
    public List getAuftragFahrernummerByAuftrags_ID(int auftrags_ID) {
        List fahrernummer;

        Query query = entityManager.createQuery(
                "SELECT a.fahrernummer " +
                        "FROM Auftrag a " +
                        "WHERE a.auftrags_ID = ?1"
        ).setParameter(1, (long) auftrags_ID);

        fahrernummer = query.getResultList();

        return fahrernummer;
    }

    @Transactional
    public Auftrag getAuftragByID(int id) {
        return find("Auftrags_ID", id).firstResult();
    }

    @Transactional
    public List<Auftrag> getAllAuftragByRestaurant(long id) {
        return find("Auftragnehmer", id).list();
    }

    @Transactional
    public List<Auftrag> getAllAuftraegeByKunde(int id) {
        return find("Kundennummer", id).list();
    }

    @Transactional
    public List<Auftrag> getAllAuftaregeByKundenId(long id){
        return find("Kundennummer", id).list();
    }

    @Transactional
    public void setToErledigt(int id) { update("status = 'erledigt' where id = ?1", (long) id);
    }
}
