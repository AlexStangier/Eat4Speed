package de.eat4speed.repositories;

import de.eat4speed.entities.Fahrer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

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
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=0;").executeUpdate();
        persist(fahrer);
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=1;").executeUpdate();

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
    public List getFahrer(int benutzer_Id)
    {
        List fahrer;

        Query query = entityManager.createQuery(
                "SELECT f.fahrernummer  " +
                        "FROM Fahrer f " +
                        "WHERE f.benutzer_ID = " + benutzer_Id + " "
        );
        fahrer =  query.getResultList();
        return fahrer;
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

    @Transactional
    public List get_Fahrer_Fzg_Pos() {

        List <Object[]> fahrer_fzg;

        Query query = entityManager.createQuery(
                "SELECT fz.fahrzeugtyp, ad.lng, ad.lat, fz.kapazitaet_Gerichte " +
                        "from Fahrzeug fz ,Fahrer fa,Benutzer b, Adressen ad " +
                        "where fz.fahrzeug_ID = fa.fahrzeug " +
                        "AND fa.benutzer_ID = b.benutzer_ID " +
                        "AND ad.adress_ID = fa.aktueller_Standort " +
                        "AND b.emailAdresse " +
                        "like 'arturs@arturs.de'"
        );


        fahrer_fzg = query.getResultList();
        List<String> results = new ArrayList<>(fahrer_fzg.size());

        for(Object[] objects : fahrer_fzg){
            results.add(new String((String) objects[0]));
            results.add(new String((String) objects[1]));
            results.add(new String((String) objects[2]));
            results.add(new String((String) objects[3].toString()));
        }

        if(results.get(0).equals("Fahrrad")){
            results.set(0,"bycicle");
        }
        else{
            results.set(0,"drive");
        }

        return results;
    }

    @Transactional
    public List get_Restautant_Lng_Lat(){

        List <Object[]> restaurant_lng_lat;


        Query query = entityManager.createQuery(
                "select lng, lat " +
                        "from Adressen " +
                        "where adress_ID " +
                        "IN (select anschrift from Restaurant where restaurant_ID " +
                        "IN (select auftragnehmer from Auftrag)) "
        );




        restaurant_lng_lat = query.getResultList();
        List<String> results = new ArrayList<>(restaurant_lng_lat.size());

        int i = 0;
        for(Object[] objects : restaurant_lng_lat){
            while(i < objects.length){
                results.add(new String((String) objects[i]));
                i++;
            }
            i = 0;
        }

        return results;

    }

    @Transactional
    public List get_Kunde_Lng_Lat(){

        List <Object[]> kunde_lng_lat;

        Query query = entityManager.createQuery(
                "select lng, lat " +
                        "from Adressen " +
                        "where adress_ID " +
                        "IN (select anschrift from Kunde where kundennummer " +
                        "IN (select kundennummer from Auftrag) )"
        );



        kunde_lng_lat = query.getResultList();
        List<String> results = new ArrayList<>(kunde_lng_lat.size());

        int i = 0;
        for(Object[] objects : kunde_lng_lat){
            while(i < objects.length){
                results.add(new String((String) objects[i]));
                i++;
            }
            i = 0;
        }

        return results;

    }
}
