package de.eat4speed.Algo;

import de.eat4speed.entities.*;
import de.eat4speed.repositories.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class Algo_FahrerAuswahl {

    private SchichtRepository schichtRepository = new SchichtRepository();
    private FahrerRepository fahrerRepository = new FahrerRepository();
    private FahrtenplanRepository fahrtenplanRepository = new FahrtenplanRepository();
    private FahrzeugRepository fahrzeugRepository = new FahrzeugRepository();
    private UrlaubRepository urlaubRepository = new UrlaubRepository();
    private AuftragRepository auftragRepository = new AuftragRepository();
    private BestellungRepository bestellungRepository = new BestellungRepository();

    private Fahrtenplan_Station startPunkt;
    private Auftrag start;
    private final int sekunden = 30;
    private final int maxFahrer = 5;
    private int anzahlGerichte;
    private List<Integer> RestaurantIDs = new ArrayList<>();

    // Sende Auftrag an besten Fahrer, falls nach 30 sekunden nicht angenommen
    // sende zusätzlich an nächstbesten fahrer falls keine fahrer mehr da oder 5min vergangen sind
    // -> neustart
    public void Fahrtenvergabe(int startPunktID) {

        int count = 0;
        start = auftragRepository.getAuftragByID(startPunktID);
        //startPunkt = fahrtenplanRepository.findByStationsID(startPunktID);
        //anzahlGerichte = AnzahlGerichte(startPunktID);

        System.out.println(startPunktID);
        anzahlGerichte = AnzahlGerichte(startPunktID);
        List<Fahrer_Distanz> naheFahrer = new ArrayList<>();
        List<Integer> BenachrichtigungsIDs = new ArrayList<>();

        //if(true)
        //return;
        boolean isRunning = true;
        boolean restart = false;

        while(isRunning) {

            if (false)
            //if (AuftragAngenommen(startPunktID))
            {
                // evtl anfragen entfernen
                for (int i = 0; i < count; i++)
                {
                    entferne_Auftrag_von_Fahrer(BenachrichtigungsIDs, startPunkt.getAuftrag());
                }
                System.out.println("Auftrag wurde angenommen");
                break;
            }

            // start
            if (count == 0)
            {
                System.out.println("Start");
                naheFahrer = getNaheFahrer();
                if(true)
                    return;
            }

            if (restart)
            {
                // anfragen entfernen und neustart
                entferne_Auftrag_von_Fahrer(BenachrichtigungsIDs, startPunkt.getAuftrag());

                restart(startPunktID);
                isRunning = false;
            }
            // sende alle 30 sek an nächstbesten Fahrer
            else if (naheFahrer.size() > 0 )
            {
                if (naheFahrer.size() > count && count < maxFahrer)
                {
                    // wenn noch an unter maxFahrer gesendet
                    System.out.println(count + " Schicke an Fahrer mit ID: "+naheFahrer.get(count)
                    + " "  +LocalDateTime.now());
                    Schicht schicht = schichtRepository.getSchichtHeute(naheFahrer.get(count).getFahrer_ID());
                    BenachrichtigungsIDs.add(sende_Auftrag_an_Fahrer(naheFahrer.get(count).getFahrer_ID(), startPunkt.getAuftrag(), naheFahrer.get(count).getRestaurant_ID()));
                    count++;
                }
                else
                {
                    // wenn Liste durch und nicht angenommen
                    System.out.println("Restarting in 30s " + Thread.currentThread().getId()+ " "  +LocalDateTime.now());
                    restart = true;
                }
            }
            else
            {
                System.out.println("No Found -> Restarting in 30s " + Thread.currentThread().getId() + " " +
                        " "  +LocalDateTime.now());
                restart = true;
            }
            try
            {
                Thread.sleep(sekunden * 1000);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void restart(int startPunktID)
    {
        URL url;
        System.out.println("Restarting" + " "  + LocalDateTime.now());
        try {
            url = new URL("http://localhost:1337/FahrerAuswahl/" + startPunktID);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("PUT");
            http.setDoOutput(false);
            http.setReadTimeout(5 * 1000);
            http.getInputStream();

            http.disconnect();
        } catch (Exception e) {
            System.out.println("Restarted: " + startPunktID + " "  + LocalDateTime.now());
        }
    }

    private int AnzahlGerichte(int auftragsID)
    {
        int count = 0;
        //int StationsID = startpunktID;
        //List<Integer> AuftragIDs = new ArrayList<>();

        List<Bestellung> bestellungen = bestellungRepository.find("Auftrags_ID", auftragsID).list();
        for (Bestellung b : bestellungen)
        {
            count += new JSONArray(b.getGericht_IDs()).length();
            RestaurantIDs.add(b.getRestaurant_ID());
        }
        /*
        AuftragIDs.add(startPunkt.getAuftrag());

        boolean hasNext = true;

        while (hasNext)
        {
            Fahrtenplan_Station next = fahrtenplanRepository.findByStationsID(StationsID);
            if (next.getNaechste_Station() != null)
            {
                StationsID = next.getNaechste_Station();
                AuftragIDs.add(next.getAuftrag());
            }
            else
            {
                hasNext = false;
            }
        }

        for (Integer i : AuftragIDs)
        {
            count += getGerichte(i).length();
        }
*/
        System.out.println("Gerichte: " + count);

        return count;
    }

    //TODO
    private boolean AuftragAngenommen(int id)
    {
        boolean retVal = false;
        Integer fahrerID = null;

        try {
            URL url = new URL("http://localhost:1337/Fahrtenplan/" + id);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(true);

            fahrerID = (Integer)new JSONObject(getResponse(http.getInputStream())).get("Fahrer");

            if (fahrerID != null)
            {                System.out.println("NOT NULL " + fahrerID);
                retVal = true;
            }

        } catch (ClassCastException e) {
            System.out.println("NULL " + fahrerID);
            retVal = false;
        } catch (IOException e){
            e.printStackTrace();
        }

        return retVal;
    }

    private int sende_Auftrag_an_Fahrer(int fahrerID, int auftragID, int restaurantID)
    {
        String benachrichtigung = "Auftrag Anfrage " + auftragID;
        Benachrichtigung_Fahrer benachrichtigung_fahrer = new Benachrichtigung_Fahrer();
        benachrichtigung_fahrer.setBenachrichtigungs_ID(0);
        benachrichtigung_fahrer.setFahrernummer(fahrerID);
        benachrichtigung_fahrer.setRestaurant_ID(restaurantID);
        benachrichtigung_fahrer.setGelesen((byte)0);
        benachrichtigung_fahrer.setBenachrichtigung(benachrichtigung);
        benachrichtigung_fahrer.setTimestamp(new Timestamp(new Date().getTime()));

        try {

            URL url = new URL("http://localhost:1337/Benachrichtigung_Fahrer/");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setRequestProperty("Content-Type", "application/json");

            String data = benachrichtigung_fahrer.toJSON().toString();

            byte[] out = data.getBytes(StandardCharsets.UTF_8);

            OutputStream stream = http.getOutputStream();
            stream.write(out);

            http.getInputStream();
            http.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        int id = getBenachrichtigungID(benachrichtigung, fahrerID);

        BenachrichtigungFahrerAuftrag anfrage = new BenachrichtigungFahrerAuftrag();
        anfrage.setBenachrichtigungs_ID(id);
        anfrage.setAuftrags_ID(auftragID);

        try {

            URL url = new URL("http://localhost:1337/BenachrichtigungFahrerAuftrag/");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("PUT");
            http.setDoOutput(true);
            http.setRequestProperty("Content-Type", "application/json");

            String data = anfrage.toJSON().toString();

            byte[] out = data.getBytes(StandardCharsets.UTF_8);

            OutputStream stream = http.getOutputStream();
            stream.write(out);

            http.getInputStream();

            http.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    private int getBenachrichtigungID(String benachrichtigung, int fahrernummer)
    {
        int id = 0;

        try {
            URL url = new URL("http://localhost:1337/Benachrichtigung_Fahrer/id");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setRequestProperty("Content-Type", "application/json");

            JSONObject json = new JSONObject()
                    .put("benachrichtigung", benachrichtigung)
                    .put("fahrernummer", fahrernummer);

            String data = json.toString();

            byte[] out = data.getBytes(StandardCharsets.UTF_8);

            OutputStream stream = http.getOutputStream();
            stream.write(out);

            id = new JSONObject(getResponse(http.getInputStream())).getInt("id");

            http.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return id;
    }

    public static String getResponse(InputStream ips) throws IOException {

        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(
                new InputStreamReader(ips, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }
        return textBuilder.toString();
    }

    private void entferne_Auftrag_von_Fahrer(List<Integer> BenachrichtigungsIDs, int auftragID)
    {
        for (int i : BenachrichtigungsIDs)
        {
            try {
                URL url = new URL("http://localhost:1337/BenachrichtigungFahrerAuftrag/" + auftragID + "/" + i);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("DELETE");
                http.setDoOutput(false);
                http.getInputStream();
                http.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                URL url = new URL("http://localhost:1337/Benachrichtigung_Fahrer/" + i);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("DELETE");
                http.setDoOutput(false);
                http.getInputStream();
                http.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Fahrer_Distanz> getNaheFahrer()
    {
        List<Fahrer> fahrer = fahrerRepository.getEveryVerifiedFahrer();
        List<Fahrer_Distanz> naheFahrer = new ArrayList<>();

        System.out.println("Fahrer gefunden: " + fahrer.size());

        SortByDistanz sortByDistanz = new SortByDistanz(startPunkt);
        List<Fahrer_Distanz> distances;

        // sortieren nach nähe zum Startpunkt der Stationen
        if (fahrer.size() > 0)
        {
            distances = sortByDistanz.getDistances(fahrer, RestaurantIDs);

            for (int i = 0; i < fahrer.size(); i++)
            {
                if (!CheckFahrerAvailability(fahrer.get(i), distances.get(i).getFahrzeit())) {
                    distances.remove(i);
                    fahrer.remove(i);
                    i--;
                }
            }

            System.out.println("zur Verfuegung: " + fahrer.size());

            distances.sort( sortByDistanz );

            List<Integer> da = new ArrayList<>();
            for (int i = 0; i < distances.size() && i < maxFahrer; i++)
            {
                if(!da.contains(distances.get(i).getFahrer_ID()))
                {
                    da.add(distances.get(i).getFahrer_ID());
                    naheFahrer.add(distances.get(i));
                }
            }

        }
        return naheFahrer;
    }

    //TODO
    private boolean CheckFahrerAvailability(Fahrer fahrer, Long Fahrzeit)
    {
        boolean isAvailable = false;

        //TODO
        // fahrer anzahl aktueller Aufträge
        //TODO
        // Schicht nur Uhrzeit, Urlaub nur Datum
        if (fahrer.getAnzahl_Aktueller_Auftraege() < 1 && fahrer.getIst_in_Pause() == 0)
        {
            //Urlaub urlaub = urlaubRepository.find("fahrernummer", fahrer.getFahrernummer()).firstResult();

            Schicht schicht = schichtRepository.getSchichtHeute(fahrer.getFahrernummer());
            Fahrzeug fahrzeug = fahrzeugRepository.findByFahrzeugID(fahrer.getFahrzeug());

            Timestamp now = new Timestamp(new Date().getTime()
                    + (startPunkt.getGeschaetzte_Fahrtzeit() * 60L * 1000L) + (Fahrzeit * 1000L));
            //urlaub.getAnfang();
            if (new Date().after(schicht.getAnfang()) && schicht.getEnde().after(now) && fahrzeug.getKapazitaet_Gerichte() >= anzahlGerichte)
            {
                isAvailable = true;
            }
        }
        return isAvailable;
    }
}