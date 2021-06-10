package de.eat4speed.Algo;

import de.eat4speed.entities.Adressen;
import de.eat4speed.entities.Fahrer;
import de.eat4speed.entities.Fahrtenplan_Station;

import de.eat4speed.repositories.AdressenRepository;
import de.eat4speed.repositories.FahrzeugRepository;

import de.eat4speed.repositories.RestaurantRepository;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
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
        //this.startAdresse = adressenRepository.getAdresseByCustomerId(startPunktAuftrag.getLiefer_Abholadresse());
    }

    @Override
    public int compare(Fahrer_Distanz f1, Fahrer_Distanz f2) {
        return Long.compare( f1.getDistanz(), f2.getDistanz());
    }

    public List<Fahrer_Distanz> getDistances(List<Fahrer> fahrer, List<Integer> restaurantIDs)
    {
        List<Fahrer_Distanz> distanzen = new ArrayList<>();
        RestaurantRepository restaurantRepository = new RestaurantRepository();

        for (Integer restaurant : restaurantIDs)
        {
            startAdresse = adressenRepository.getAdresseByCustomerId(restaurantRepository.findAnschriftIDFromRestaurant(restaurant));
            System.out.println(startAdresse);
            distanzen.addAll(getDistances(fahrer, restaurant));
        }

        return distanzen;
    }

    private List<Fahrer_Distanz> getDistances(List<Fahrer> fahrer, int restaurant)
    {
        List<Fahrer_Distanz> distanzen = new ArrayList<>();

        try
        {
            URL url = new URL("https://api.geoapify.com/v1/routematrix?apiKey=e15f70e37a39423cbe921dc88a1ded04");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setRequestProperty("Content-Type", "application/json");

            StringBuilder fahrerPositionen = new StringBuilder();

            for ( int i = 0; i < fahrer.size(); i++)
            {
                Adressen adresse = adressenRepository.getAdresseByCustomerId(fahrer.get(i).getAktueller_Standort());
                //fahrerPositionen.append("{ \"location\": [ " + adresse.getLng() + "," + adresse.getLat() + "] }");
                fahrerPositionen.append("{ \"location\": [ ");
                fahrerPositionen.append(adresse.getLng());
                fahrerPositionen.append(",");
                fahrerPositionen.append(adresse.getLat());
                fahrerPositionen.append("] }");

                if (i < fahrer.size() - 1)
                {
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

            JSONObject json = new JSONObject(Algo_FahrerAuswahl.getResponse(http.getInputStream()));

            JSONArray jarray = (JSONArray) json.get("sources_to_targets");

            for (int i = 0; i < jarray.length(); i++)
            {
                JSONObject info = jarray.getJSONArray(i).getJSONObject(0);
                distanzen.add(new Fahrer_Distanz(fahrer.get(i).getFahrernummer(), restaurant,
                        info.getLong("distance"), info.getLong("time")));
            }

            http.disconnect();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return distanzen;
    }

}
