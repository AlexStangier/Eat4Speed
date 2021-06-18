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
    public void setPause(int pause, int fahrernummer)
    {
            update("ist_in_pause = ?1 where fahrernummer = ?2", pause, fahrernummer);
    }

    @Transactional
    public Object getAmountInPause()
    {
        Object amount;
        Query query = entityManager.createQuery(
                "SELECT count(f.ist_in_Pause)" +
                        "FROM Fahrer f " +
                        "WHERE f.ist_in_Pause = 1 "
        );
        amount = query.getSingleResult();
        return amount;
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
                "SELECT f.fahrernummer, f.ist_in_Pause  " +
                        "FROM Fahrer f " +
                        "WHERE f.benutzer_ID = " + benutzer_Id + " "
        );
        fahrer =  query.getResultList();
        return fahrer;
    }
  
    @Transactional
    public void updateFahrer_anzahl_aktueller_Auftraege(int fahrernummer, int anzahl)
    {
        update("anzahl_Aktueller_Auftraege = ?1 where fahrernummer = ?2",anzahl, fahrernummer);
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
    public ArrayList<String> get_Fahrer_Fzg_Pos(String email) {

        List <Object[]> fahrer_fzg;

        Query query = entityManager.createQuery(
                "SELECT fz.fahrzeugtyp, ad.lng, ad.lat, fz.kapazitaet_Gerichte " +
                        "from Fahrzeug fz ,Fahrer fa,Benutzer b, Adressen ad " +
                        "where fz.fahrzeug_ID = fa.fahrzeug " +
                        "AND fa.benutzer_ID = b.benutzer_ID " +
                        "AND ad.adress_ID = fa.aktueller_Standort " +
                        "AND b.emailAdresse " +
                        "like ?1"
        ).setParameter(1, email);

        fahrer_fzg = query.getResultList();
        ArrayList<String> results = new ArrayList<>(fahrer_fzg.size());

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
    public ArrayList<String> get_Restautant_Lng_Lat(String email){

        List <Object[]> restaurant_lng_lat;


        Query query = entityManager.createNativeQuery(
                "SELECT Auftrag.auftrags_ID, lng, lat, name_des_Restaurants, Bestellung.status, timestamp_On_Customer_Demand " +
                        "FROM Adressen, Auftrag, Restaurant, Bestellung " +
                        "WHERE adress_ID IN (SELECT Restaurant.anschrift " +
                        "WHERE Restaurant.restaurant_ID " +
                        "IN (" +
                        "SELECT restaurant_ID " +
                        "FROM Gericht " +
                        "WHERE gericht_ID " +
                        "IN (" +
                        "SELECT json_as_rows.* " +
                        "FROM JSON_TABLE(Gericht_IDs, '$[*]' COLUMNS(gericht_ID INT PATH '$')) json_as_rows " +
                        "WHERE Bestellung.auftrags_ID = Auftrag.auftrags_ID)))" +
                        "AND Auftrag.fahrernummer " +
                        "IN (" +
                        "SELECT fahrernummer " +
                        "FROM Fahrer " +
                        "WHERE benutzer_ID = (" +
                        "SELECT benutzer_ID " +
                        "FROM Benutzer " +
                        "WHERE emailAdresse = ?1)" +
                        ")group by Auftrag.auftrags_ID, lng, lat, name_des_Restaurants, Bestellung.status, timestamp_On_Customer_Demand " +
                        "ORDER BY auftrags_ID ASC;\n"
        ).setParameter(1, email);





        restaurant_lng_lat = query.getResultList();
        ArrayList<String> results = new ArrayList<>(restaurant_lng_lat.size());

        int i = 0;
        for(Object[] objects : restaurant_lng_lat){
            while(i < objects.length){
                if(objects[i] == null){
                    results.add( "null");
                }
                else{
                    results.add( objects[i].toString());
                }
                i++;
            }
            i = 0;
        }

        return results;

    }

    @Transactional
    public ArrayList<String> get_Kunde_Lng_Lat(String email){

        List <Object[]> kunde_lng_lat;

        Query query = entityManager.createNativeQuery(
                "SELECT auftrags_ID, lng, lat, strasse, hausnummer, postleitzahl, ort " +
                        "FROM Adressen, Auftrag " +
                        "WHERE adress_ID IN ( " +
                        "SELECT anschrift " +
                        "FROM Kunde " +
                        "WHERE kundennummer IN ( " +
                        "SELECT kundennummer " +
                        "FROM Auftrag ) AND Kunde.kundennummer = Auftrag.kundennummer ) AND Auftrag.fahrernummer IN ( " +
                        "SELECT fahrernummer " +
                        "FROM Fahrer " +
                        "WHERE benutzer_ID = ( " +
                        "SELECT benutzer_ID " +
                        "FROM Benutzer " +
                        "WHERE emailAdresse like ?1 ))  group by auftrags_ID, lng, lat, strasse, hausnummer, ort, postleitzahl order by auftrags_id ASC"
        ).setParameter(1, email);



        kunde_lng_lat = query.getResultList();
        ArrayList<String> results = new ArrayList<>(kunde_lng_lat.size());

        int i = 0;
        for(Object[] objects : kunde_lng_lat){
            while(i < objects.length){
                results.add(objects[i].toString());
                i++;
            }
            i = 0;
        }

        return results;

    }

    @Transactional
    public void set_Bestellung_abgeholt(String rest_name, int auftr_id){
        Query query = entityManager.createQuery(
                "UPDATE Bestellung " +
                        "SET status = 'abgeholt' " +
                        "WHERE restaurant_ID = " +
                        "(SELECT restaurant_ID FROM Restaurant WHERE name_des_Restaurants like ?1) AND auftrags_ID = ?2"
        ).setParameter(1, rest_name).setParameter(2, auftr_id);
        query.executeUpdate();
    }

    @Transactional
    public void set_Fahrer_aktuellePos_Abholung(String restaurant_name, String email){
        Query query = entityManager.createNativeQuery(
                "UPDATE Fahrer " +
                        "SET aktueller_Standort = " +
                        "(SELECT anschrift FROM Restaurant WHERE name_des_Restaurants like ?1 ) " +
                        "WHERE fahrernummer = " +
                        "(SELECT fahrernummer WHERE Fahrer.benutzer_ID = (SELECT benutzer_ID FROM Benutzer WHERE emailAdresse like ?2 ))"
        ).setParameter(1, restaurant_name).setParameter(2, email);
        query.executeUpdate();
    }

    @Transactional
    public void set_Fahrer_aktuellePos_Ablieferung(long auftrags_id, String email){
        Query query = entityManager.createNativeQuery("UPDATE Fahrer " +
                "SET aktueller_Standort = " +
                "(SELECT anschrift " +
                "FROM Kunde " +
                "WHERE kundennummer = " +
                "(SELECT Kundennummer FROM Auftrag WHERE Auftrags_ID = ?1) ) " +
                "WHERE fahrernummer = (SELECT fahrernummer WHERE Fahrer.benutzer_ID = " +
                "(SELECT benutzer_ID FROM Benutzer " +
                "WHERE emailAdresse like ?2 ))"
        ).setParameter(1, auftrags_id).setParameter(2, email);
        query.executeUpdate();
    }

    @Transactional
    public void set_Bestellung_abgeliefert(int auftr_id){
        Query query = entityManager.createQuery(
                "UPDATE Bestellung " +
                        "SET status = 'abgeliefert' " +
                        "WHERE status = 'abgeholt' AND auftrags_ID = ?1"
        ).setParameter(1, auftr_id);
        query.executeUpdate();
    }

    @Transactional
    public void set_auftrags_zeit(long auftr_id, java.sql.Timestamp dt){
        Query query = entityManager.createNativeQuery(
                "UPDATE Auftrag " +
                        "SET timestamp_Lieferung = ?1 " +
                        "WHERE auftrags_ID = ?2 "
        ).setParameter(1, dt).setParameter(2, auftr_id);
        query.executeUpdate();
    }
}
