package de.eat4speed.repositories;

import de.eat4speed.entities.Adressen;
import de.eat4speed.entities.Fahrer;
import de.eat4speed.entities.Kunde;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jdk.nashorn.internal.parser.JSONParser;
import org.drools.core.runtime.help.impl.XStreamJSon;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FahrerRepository implements PanacheRepository<Fahrer> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void addFahrer(Fahrer fahrer)
    {
        persist(fahrer);
    }

    @Transactional
    public void updateFahrer_Fahrzeug_id(int fahrernummer, int fahrzeug_Id)
    {
        update("fahrzeug = ?1 where fahrernummer = ?2", fahrzeug_Id, fahrernummer);
    }

    @Transactional
    public void updateFahrer_Verifiziert(int fahrernummer)
    {
        update("verifiziert = 1 where fahrernummer = ?1", fahrernummer);
    }

    @Transactional
    public List getAllFahrer()
    {
        List allFahrerData;

                Query query = entityManager.createQuery(
                "SELECT f.fahrernummer,b.vorname,b.nachname,fz.fahrzeugtyp,f.verifiziert,f.anrede  " +
                        "FROM Fahrer f, Benutzer b, Fahrzeug fz " +
                        "WHERE f.fahrzeug = fz.fahrzeug_ID " +
                        "AND f.benutzer_ID = b.benutzer_ID "

        );
                allFahrerData = query.getResultList();

        return allFahrerData;
    }

    @Transactional
    public List getNotVerifiedFahrer()
    {
        List notVerifiedFahrerData;

        Query query = entityManager.createQuery(
                "SELECT f.fahrernummer,b.vorname,b.nachname,fz.fahrzeugtyp,f.verifiziert,f.anrede  " +
                        "FROM Fahrer f, Benutzer b, Fahrzeug fz " +
                        "WHERE f.fahrzeug = fz.fahrzeug_ID " +
                        "AND f.benutzer_ID = b.benutzer_ID " +
                        "AND f.verifiziert = 0"

        );
        notVerifiedFahrerData = query.getResultList();

        return notVerifiedFahrerData;
    }

    @Transactional
    public List getVerifiedFahrer()
    {
        List verifiedFahrerData;

        Query query = entityManager.createQuery(
                "SELECT f.fahrernummer,b.vorname,b.nachname,fz.fahrzeugtyp,f.verifiziert,f.anrede  " +
                        "FROM Fahrer f, Benutzer b, Fahrzeug fz " +
                        "WHERE f.fahrzeug = fz.fahrzeug_ID " +
                        "AND f.benutzer_ID = b.benutzer_ID " +
                        "AND f.verifiziert = 1"

        );
        verifiedFahrerData = query.getResultList();

        return verifiedFahrerData;
    }

    @Transactional
    public int deleteFahrer(int fahrernummer)
    {
        delete("fahrernummer", fahrernummer);

        return fahrernummer;
    }


    public Fahrer findByFahrernummer(int fahrernummer)
    {
        return find("fahrernummer", fahrernummer).firstResult();
    }
}
