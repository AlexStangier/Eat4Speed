package de.eat4speed.Algo;

import de.eat4speed.entities.Fahrer;
import de.eat4speed.entities.Fahrtenplan_Station;
import de.eat4speed.entities.Fahrzeug;
import de.eat4speed.entities.Schicht;
import de.eat4speed.repositories.FahrerRepository;
import de.eat4speed.repositories.FahrtenplanRepository;
import de.eat4speed.repositories.FahrzeugRepository;
import de.eat4speed.repositories.SchichtRepository;
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

    private Fahrtenplan_Station startPunkt;
    private final int sekunden = 30;
    private final int maxFahrer = 5;
    private int anzahlGerichte;

    // Sende Auftrag an besten Fahrer, falls nach 30 sekunden nicht angenommen
    // sende zusätzlich an nächstbesten fahrer falls keine fahrer mehr da oder 5min vergangen sind
    // -> neustart
    public void Fahrtenvergabe(int startPunktID) {

        List<Integer> naheFahrerIDs = null;
        int count = 0;
        startPunkt = fahrtenplanRepository.findByStationsID(startPunktID);
        anzahlGerichte = AnzahlGerichte(startPunktID);

        boolean isRunning = true;
        boolean restart = false;

        //fahrernummer 7  4  2  3  8  6  9  5 10 11
        //fahrer      f6 f3 f1 f2 f7 f5 f8 f4 f9 f10

        while(isRunning) {

            if (AuftragAngenommen(startPunktID))
            {
                // evtl anfragen entfernen
                for (int i = 0; i < count; i++)
                {
                    entferne_Auftrag_von_Fahrer(naheFahrerIDs.get(i));
                }
                System.out.println("Auftrag wurde angenommen");
                break;
            }

            // start
            if (count == 0)
            {
                if (naheFahrerIDs != null)
                {
                    naheFahrerIDs.clear();
                }
                System.out.println("Start");
                naheFahrerIDs = getNaheFahrer();
            }

            if (restart)
            {
                // anfragen entfernen und neustart
                for (int i = 0; i < count; i++)
                {
                    entferne_Auftrag_von_Fahrer(naheFahrerIDs.get(i));
                }
                restart(startPunktID);
                isRunning = false;
            }
            // sende alle 30 sek an nächstbesten Fahrer
            else if (naheFahrerIDs.size() > 0 )
            {
                if (naheFahrerIDs.size() > count && count < maxFahrer)
                {
                    // wenn noch an unter maxFahrer gesendet
                    System.out.println(count + " Schicke an Fahrer mit ID: "+naheFahrerIDs.get(count)
                    + " "  +LocalDateTime.now());
                    Schicht schicht = schichtRepository.getSchichtHeute(naheFahrerIDs.get(count));
                    sende_Auftrag_an_Fahrer(naheFahrerIDs.get(count));
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

    public void restart(int startPunktID)
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

        } catch (Exception e) {
            System.out.println("Restarted: " + startPunktID + " "  + LocalDateTime.now());
        }
    }

    public int AnzahlGerichte(int startpunktID)
    {
        int count = 1;
        int StationsID = startpunktID;
        boolean hasNext = true;

        // TODO anzahlGerichte berechnen
        while (hasNext)
        {
            Fahrtenplan_Station next = fahrtenplanRepository.findByStationsID(StationsID);
            if (next.getNaechste_Station() != null)
            {
                StationsID = next.getNaechste_Station();
                count++;
            }
            else
            {
                hasNext = false;
            }
        }

        System.out.println("Stationen: " + count);

        return 1;
    }

    public boolean AuftragAngenommen(int id)
    {
        boolean retVal = false;
        Integer fahrerID = null;

        try {
            URL url = new URL("http://localhost:1337/Fahrtenplan/" + id);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(true);

            InputStream ips = http.getInputStream();

            StringBuilder textBuilder = new StringBuilder();

            try (Reader reader = new BufferedReader(
                    new InputStreamReader(ips, Charset.forName(StandardCharsets.UTF_8.name())))) {
                int c;
                while ((c = reader.read()) != -1) {
                    textBuilder.append((char) c);
                }
            }

            fahrerID = (Integer)new JSONObject(textBuilder.toString()).get("Fahrer");

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

    public void sende_Auftrag_an_Fahrer(int fahrerID)
    {
        // TODO
    }

    public void entferne_Auftrag_von_Fahrer(int fahrerID)
    {
        // TODO
    }

    public List<Integer> getNaheFahrer()
    {
        List<Fahrer> fahrer = fahrerRepository.getEveryVerifiedFahrer();
        List<Integer> naheFahrerIDs = new ArrayList<>();

        System.out.println("Fahrer gefunden: " + fahrer.size());


        SortByDistanz sortByDistanz = new SortByDistanz(startPunkt);
        List<Fahrer_Distanz> distances;

        // sortieren nach nähe zum Startpunkt der Stationen
        if (fahrer.size() > 0)
        {
            //fahrer.sort(sortByDistanz);
            distances =  sortByDistanz.getDistances(fahrer);

            for (int i = 0; i < fahrer.size(); i++)
            {
                if (!CheckFahrerAvailability(fahrer.get(i), distances.get(i).getFahrzeit())) {
                    fahrer.remove(fahrer.get(i));
                    i--;
                }
            }

            System.out.println("zur Verfuegung: " + fahrer.size());

            distances.sort( sortByDistanz );

            fahrer.clear();

            for (Fahrer_Distanz D : distances)
            {
                naheFahrerIDs.add(D.getFahrer_ID());
            }
        }
        return naheFahrerIDs;
    }

    public boolean CheckFahrerAvailability(Fahrer fahrer, Long Fahrzeit)
    {
        boolean isAvailable = false;

        if (fahrer.getAnzahl_Aktueller_Auftraege() < 1 && fahrer.getIst_in_Pause() == 0)
        {
            Schicht schicht = schichtRepository.getSchichtHeute(fahrer.getFahrernummer());
            Fahrzeug fahrzeug = fahrzeugRepository.findByFahrzeugID(fahrer.getFahrzeug());

            Timestamp time = new Timestamp(new Date().getTime()
                    + (startPunkt.getGeschaetzte_Fahrtzeit() * 60L * 1000L) + (Fahrzeit * 1000L));

            if (new Date().after(schicht.getAnfang()) && schicht.getEnde().after(time) && fahrzeug.getKapazitaet_Gerichte() >= anzahlGerichte)
            {
                isAvailable = true;
            }
        }
        return isAvailable;
    }
}
