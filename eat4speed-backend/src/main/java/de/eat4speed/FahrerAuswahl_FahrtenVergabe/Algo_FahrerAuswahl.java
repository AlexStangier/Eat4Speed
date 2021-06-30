package de.eat4speed.FahrerAuswahl_FahrtenVergabe;

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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class Algo_FahrerAuswahl {

    private SchichtRepository schichtRepository = new SchichtRepository();
    private FahrerRepository fahrerRepository = new FahrerRepository();
    private FahrzeugRepository fahrzeugRepository = new FahrzeugRepository();
    private AuftragRepository auftragRepository = new AuftragRepository();
    private BestellungRepository bestellungRepository = new BestellungRepository();

    private Auftrag start;
    private final int maxFahrer = 5;
    private final int maxSekunden = maxFahrer * 30;
    private int anzahlGerichte;
    private List<Integer> RestaurantIDs = new ArrayList<>();
    private boolean nichtStorniert = true;

    // Sende Auftrag an besten Fahrer, falls nach 30 sekunden nicht angenommen
    // sende zusätzlich an nächstbesten fahrer falls keine fahrer mehr da oder 5min vergangen sind
    // -> neustart
    public void Fahrtenvergabe(int auftragID) {

        int count = 0;
        boolean restart = false;

        start = auftragRepository.getAuftragByID(auftragID);
        anzahlGerichte = AnzahlGerichte(auftragID);

        List<Fahrer_Distanz> naheFahrer = new ArrayList<>();
        List<Integer> BenachrichtigungsIDs = new ArrayList<>();

        while (nichtStorniert) {

            if (AuftragAngenommen(auftragID))
            {
                // eventuelle Anfragen entfernen
                for (int i = 0; i < count; i++)
                {
                    entferne_Auftrag_von_Fahrer(BenachrichtigungsIDs, (int)start.getAuftrags_ID());
                }
                System.out.println("Auftrag wurde angenommen");
                break;
            }

            // start
            if (count == 0)
            {
                System.out.println("Start");
                naheFahrer = getNaheFahrer();
            }

            if (restart)
            {
                // anfragen entfernen und neustart
                entferne_Auftrag_von_Fahrer(BenachrichtigungsIDs, (int)start.getAuftrags_ID());

                restart(auftragID);

                break;
            }
            // sende alle 30 sek an nächstbesten Fahrer
            else if (naheFahrer.size() > 0 )
            {
                if (naheFahrer.size() > count && count < maxFahrer)
                {
                    // wenn noch an unter maxFahrer gesendet
                    System.out.println(count + " Schicke an Fahrer mit ID: " + naheFahrer.get(count).getFahrer().getFahrernummer()
                        + " "  + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));

                    BenachrichtigungsIDs.add(sende_Auftrag_an_Fahrer(naheFahrer.get(count).getFahrer().getFahrernummer(),
                        (int)start.getAuftrags_ID(), naheFahrer.get(count).getRestaurant_ID()));

                    count++;
                }
                else
                {
                    // wenn Liste durch und nicht angenommen
                    System.out.println("Restarting in 30s | " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));

                    restart = true;
                }
            }
            else
            {
                System.out.println("No Found -> Restarting in 30s | "  + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));

                restart = true;
            }
            try
            {
                AnzahlGerichte(auftragID);
                Thread.sleep(((naheFahrer.size() > 0) ? (maxSekunden / naheFahrer.size()) : 30) * 1000);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < count; i++)
        {
            entferne_Auftrag_von_Fahrer(BenachrichtigungsIDs, (int)start.getAuftrags_ID());
        }
    }

    private void restart(int startPunktID)
    {
        URL url;
        try {
            url = new URL("http://localhost:1337/FahrerAuswahl/" + startPunktID);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("PUT");
            http.setDoOutput(false);
            http.setReadTimeout(10);
            http.getInputStream();

            http.disconnect();
        } catch (Exception e) {
            System.out.println("Restarting: " + startPunktID + " | "  + LocalDateTime.now());
        }
    }

    private int AnzahlGerichte(int auftragsID)
    {
        int count = 0;

        List<Bestellung> bestellungen = bestellungRepository.find("Auftrags_ID", auftragsID).list();

        for (Bestellung b : bestellungen)
        {
            if (b.getStatus().equals("abholbereit"))
            {
                count += new JSONArray(b.getGericht_IDs()).length();
                RestaurantIDs.add(b.getRestaurant_ID());
            }
        }

        nichtStorniert = (RestaurantIDs.size() > 0);

        if (nichtStorniert)
        {
            System.out.println("Gerichte: " + count);
        }
        else
        {
            System.out.println(auftragsID + " wurde storniert");
        }

        return count;
    }

    private boolean AuftragAngenommen(int id)
    {
        boolean retVal = false;

        try
        {
            URL url = new URL("http://localhost:1337/Auftrag/getAuftragFahrernummerByAuftrags_ID/" + id);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(true);

            JSONArray jarray = new JSONArray(getResponse(http.getInputStream()));

            retVal = !(jarray.getLong(0) == 9999);

        } catch (IOException e){
            e.printStackTrace();
        }

        return retVal;
    }

    private int sende_Auftrag_an_Fahrer(int fahrerID, int auftragID, int restaurantID)
    {
        String benachrichtigung = "Auftrag Anfrage " + auftragID;
        Benachrichtigung_Fahrer benachrichtigung_fahrer = new Benachrichtigung_Fahrer(0, fahrerID,
                benachrichtigung, restaurantID, new Timestamp(new Date().getTime()), (byte)0);

        try
        {
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

        try
        {
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

        try
        {
            URL url = new URL("http://localhost:1337/Benachrichtigung_Fahrer/id");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
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

    public static String getResponse(InputStream ips) throws IOException
    {
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
            try
            {
                URL url = new URL("http://localhost:1337/BenachrichtigungFahrerAuftrag/" + auftragID + "/" + i);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("DELETE");
                http.setDoOutput(false);
                http.getInputStream();
                http.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
            }

            try
            {
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
        List<Fahrer_Distanz> naheFahrer = new ArrayList<>();
        SortByDistanz sortByDistanz = new SortByDistanz();
        List<Fahrer_Distanz> distances;
        List<Fahrer> fahrer = fahrerRepository.getEveryAvailableFahrer();

        for (int i = 0; i < fahrer.size(); i++)
        {
            if (istImUrlaub(fahrer.get(i).getFahrernummer()) | fahrer.get(i).getAktueller_Standort() == 0)
            {
                fahrer.remove(i);
                i--;
            }
        }

        System.out.println("Fahrer gefunden: " + fahrer.size());

        // sortieren nach nähe zum Startpunkt der Stationen
        if (fahrer.size() > 0)
        {
            distances = sortByDistanz.getDistances(fahrer, RestaurantIDs);
            distances.sort( sortByDistanz );

            double getGeschaetzte_Fahrtzeit = sortByDistanz.getFahrzeit(start.getKundennummer());

            List<Integer> da = new ArrayList<>();

            for (int i = 0; i < distances.size() && naheFahrer.size() < maxFahrer; i++)
            {
                if (CheckFahrerAvailability(distances.get(i).getFahrer(), distances.get(i).getFahrzeit(), getGeschaetzte_Fahrtzeit))
                {
                    if (!da.contains(distances.get(i).getFahrer().getFahrernummer()))
                    {
                        da.add(distances.get(i).getFahrer().getFahrernummer());
                        naheFahrer.add(distances.get(i));
                    }
                }
            }
            System.out.println("zur Verfuegung: " + naheFahrer.size());
        }
        return naheFahrer;
    }

    private boolean CheckFahrerAvailability(Fahrer fahrer, Long Fahrzeit, double getGeschaetzte_Fahrtzeit)
    {
        boolean isAvailable = false;

        if (fahrer.getAnzahl_Aktueller_Auftraege() < 1)
        {
            Timestamp endeAuftrag = new Timestamp( new Date().getTime()
                    + (long)(getGeschaetzte_Fahrtzeit * 1000L) + (Fahrzeit * 1000L));

            Schicht schicht = schichtRepository.getSchichtHeute(fahrer.getFahrernummer());
            Fahrzeug fahrzeug = fahrzeugRepository.findByFahrzeugID(fahrer.getFahrzeug());

            if (new Date().after(schicht.getAnfang()) && schicht.getEnde().after(endeAuftrag) && fahrzeug.getKapazitaet_Gerichte() >= anzahlGerichte)
            {
                isAvailable = true;
            }
        }
        return isAvailable;
    }

    private boolean istImUrlaub(int fahrernummer)
    {
        boolean heute_im_Urlaub = false;

        try
        {
            URL url = new URL("http://localhost:1337/Urlaub/HeuteUrlaub/" + fahrernummer);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(true);

            heute_im_Urlaub = Boolean.getBoolean(getResponse(http.getInputStream()));

        } catch (IOException e){
            e.printStackTrace();
        }
        return heute_im_Urlaub;
    }
}
