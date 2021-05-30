package de.eat4speed.Algo;

import de.eat4speed.entities.Adressen;
import de.eat4speed.entities.Fahrer;
import de.eat4speed.entities.Fahrtenplan_Station;

import de.eat4speed.repositories.AdressenRepository;
import de.eat4speed.repositories.FahrzeugRepository;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class SortByDistanz implements Comparator<Fahrer_Distanz> {

    private Adressen startAdresse;

    private AdressenRepository adressenRepository;
    private FahrzeugRepository fahrzeugRepository;

    SortByDistanz(Fahrtenplan_Station startPunktAuftrag)
    {
        this.adressenRepository = new AdressenRepository();
        this.fahrzeugRepository = new FahrzeugRepository();
        this.startAdresse = adressenRepository.getAdresseByID(startPunktAuftrag.getLiefer_Abholadresse());
    }

    /*
    public JSONObject loc(String mode, double[] lng, double[] lat) {

        JSONObject obj = new JSONObject()
                .put("mode", mode)
                .put("sources", new JSONArray()
                        .put(new JSONObject()
                                .put("location", new JSONArray()
                                        .put(lng[0]).put(lat[0])
                                )
                        )
                )
                .put("targets", new JSONArray()
                        .put(new JSONObject()
                                .put("locations", new JSONArray()
                                        .put(lng[1]).put(lat[1])
                                )
                        )
                );
        return obj;
    }
    */
/*
    public List<Fahrer_Distanz> getDistances_with_mode(List<Fahrer> fahrer)
    {
        List<Fahrer_Distanz> distanzen = new ArrayList<>();

        for (int i = 0; i < fahrer.size(); i++)
        {
            try
            {
                URL url = new URL("https://api.geoapify.com/v1/routematrix?apiKey=e15f70e37a39423cbe921dc88a1ded04");
                HttpURLConnection http = (HttpURLConnection)url.openConnection();
                http.setRequestMethod("POST");
                http.setDoOutput(true);
                http.setRequestProperty("Content-Type", "application/json");

                Adressen adresse = adressenRepository.getAdresseByID(fahrer.get(i).getAktueller_Standort());
                Fahrzeug fahrzeug = fahrzeugRepository.findByFahrzeugID(fahrer.get(i).getFahrzeug());

                JSONObject jsonObject = loc(fahrzeug.getFahrzeugtyp(),
                    new double[]{ Double.parseDouble(adresse.getLng()), Double.parseDouble(startAdresse.getLng()) },
                        new double[]{ Double.parseDouble(adresse.getLat()), Double.parseDouble(startAdresse.getLat()) } );

                String data =
                        "{" +
                                "\"mode\": \"drive\"," +
                                "\"sources\": [" +
                                //fahrerPositionen +
                                fahrerPositionen +
                                //"{ \"location\": [ 10.836284570309772, 48.41256954594283] }," +
                                //"{ \"location\": [ 11.66300576171568, 48.4344417659791] }," +
                                //"{ \"location\": [ 11.910198144528408, 48.22444577934331] }" +
                                "]," +
                                "\"targets\": [" +
                                "{ \"location\": [" + startAdresse.getLng() + "," + startAdresse.getLat() + "] }" +
                                "]" +
                                "}";

                byte[] out = data.getBytes(StandardCharsets.UTF_8);

                OutputStream stream = http.getOutputStream();
                //stream.write(out);

                InputStream ips = http.getInputStream();

                StringBuilder textBuilder = new StringBuilder();
                try (Reader reader = new BufferedReader(
                        new InputStreamReader(ips, Charset.forName(StandardCharsets.UTF_8.name())))) {
                    int c;
                    while ((c = reader.read()) != -1) {
                        textBuilder.append((char) c);
                    }
                }

                JSONObject json = new JSONObject(textBuilder.toString());

                JSONArray jarray = (JSONArray) json.get("sources_to_targets");

                for (int j = 0; j < jarray.length(); j++)
                {
                    JSONObject info = jarray.getJSONArray(j).getJSONObject(0);
                    distanzen.add(new Fahrer_Distanz(fahrer.get(j).getFahrernummer(),
                            info.getLong("distance"), info.getLong("time")));
                }

                http.disconnect();

            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        return distanzen;
    }
*/

    @Override
    public int compare(Fahrer_Distanz f1, Fahrer_Distanz f2) {
        return Long.compare( f1.getDistanz(), f2.getDistanz());
    }

    public List<Fahrer_Distanz> getDistances(List<Fahrer> fahrer)
    {
        List<Fahrer_Distanz> distanzen = new ArrayList<>();

        try
        {
            URL url = new URL("https://api.geoapify.com/v1/routematrix?apiKey=e15f70e37a39423cbe921dc88a1ded04");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setRequestProperty("Content-Type", "application/json");

            //String fahrerPositionen = "";

            StringBuilder fahrerPositionen = new StringBuilder();

            for ( int i = 0; i < fahrer.size(); i++)
            {
                Adressen adresse = adressenRepository.getAdresseByID(fahrer.get(i).getAktueller_Standort());
                //fahrerPositionen.append("{ \"location\": [ " + adresse.getLng() + "," + adresse.getLat() + "] }");
                fahrerPositionen.append("{ \"location\": [ ");
                fahrerPositionen.append(adresse.getLng());
                fahrerPositionen.append(",");
                fahrerPositionen.append(adresse.getLat());
                fahrerPositionen.append("] }");
                //fahrerPositionen += "{ \"location\": [ " + adresse.getLng() + "," + adresse.getLat() + "] }";

                if (i < fahrer.size() - 1)
                {
                    //fahrerPositionen += ",";
                    fahrerPositionen.append(",");
                }

            }

            String data =
                    "{" +
                        "\"mode\": \"drive\"," +
                        "\"sources\": [" +
                            //fahrerPositionen +
                            fahrerPositionen +
                            //"{ \"location\": [ 10.836284570309772, 48.41256954594283] }," +
                            //"{ \"location\": [ 11.66300576171568, 48.4344417659791] }," +
                            //"{ \"location\": [ 11.910198144528408, 48.22444577934331] }" +
                        "]," +
                        "\"targets\": [" +
                            "{ \"location\": [" + startAdresse.getLng() + "," + startAdresse.getLat() + "] }" +
                        "]" +
                    "}";

            byte[] out = data.getBytes(StandardCharsets.UTF_8);

            OutputStream stream = http.getOutputStream();
            stream.write(out);

            InputStream ips = http.getInputStream();

            StringBuilder textBuilder = new StringBuilder();
            try (Reader reader = new BufferedReader(
                    new InputStreamReader(ips, Charset.forName(StandardCharsets.UTF_8.name())))) {
                int c;
                while ((c = reader.read()) != -1) {
                    textBuilder.append((char) c);
                }
            }

            JSONObject json = new JSONObject(textBuilder.toString());

            JSONArray jarray = (JSONArray) json.get("sources_to_targets");

            for (int i = 0; i < jarray.length(); i++)
            {
                JSONObject info = jarray.getJSONArray(i).getJSONObject(0);
                distanzen.add(new Fahrer_Distanz(fahrer.get(i).getFahrernummer(),
                        info.getLong("distance"), info.getLong("time")));
            }

            http.disconnect();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return distanzen;
    }

    /*
    // Alt(Radius)
    @Override
    public int compare(Fahrer f1, Fahrer f2) {

        Adressen Fahrer1_Adresse = adressenRepository.getAdresseByID(f1.getAktueller_Standort());
        Adressen Fahrer2_Adresse = adressenRepository.getAdresseByID(f2.getAktueller_Standort());

        double F1_Lat = Double.parseDouble(Fahrer1_Adresse.getLat());
        double F1_Lng = Double.parseDouble(Fahrer1_Adresse.getLng());
        double F2_Lat = Double.parseDouble(Fahrer2_Adresse.getLat());
        double F2_Lng = Double.parseDouble(Fahrer2_Adresse.getLng());

        double Start_Lat = Double.parseDouble(startAdresse.getLat());
        double Start_Lng = Double.parseDouble(startAdresse.getLng());

        return Double.compare(distance(F1_Lat, Start_Lat, F1_Lng, Start_Lng, 0, 0),
               distance(F2_Lat, Start_Lat, F2_Lng, Start_Lng, 0, 0));
    }
*/
    public static double distance(double lat1, double lat2, double lon1, double lon2, double el1, double el2)
    {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

}
