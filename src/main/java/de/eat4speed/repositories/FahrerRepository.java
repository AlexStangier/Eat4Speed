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
    public List getAllFahrer()
    {
        List allFahrerData;

        //"SELECT f.fahrernummer,b.vorname,b.nachname,fz.fahrzeugtyp,f.verifiziert " +

        //                "SELECT f.fahrernummer AS fahrernummer, b.vorname AS vorname, b.nachname AS nachname, fz.fahrzeugtyp AS fahrzeugtyp, f.verifiziert AS verifiziert" +
                Query query = entityManager.createQuery(
                "SELECT f.fahrernummer,b.vorname,b.nachname,fz.fahrzeugtyp,f.verifiziert  " +
                        "FROM Fahrer f, Benutzer b, Fahrzeug fz " +
                        "WHERE f.fahrzeug = fz.fahrzeug_Id " +
                        "AND f.benutzer_Id = b.benutzer_ID "

        );
                allFahrerData = query.getResultList();

        return allFahrerData;
    }



    public Fahrer findByFahrernummer(int fahrernummer)
    {
        return find("fahrernummer", fahrernummer).firstResult();
    }
}
