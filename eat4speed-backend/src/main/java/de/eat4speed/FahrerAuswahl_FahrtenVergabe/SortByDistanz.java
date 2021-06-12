package de.eat4speed.FahrerAuswahl_FahrtenVergabe;

import de.eat4speed.entities.Adressen;
import de.eat4speed.entities.Fahrer;
import de.eat4speed.repositories.AdressenRepository;
import de.eat4speed.repositories.KundeRepository;
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
    private List<Adressen> RestaurantAdressen;

    SortByDistanz()
    {
        this.adressenRepository = new AdressenRepository();
    }

    @Override
    public int compare(Fahrer_Distanz f1, Fahrer_Distanz f2) {
        return Long.compare( f1.getDistanz(), f2.getDistanz());
    }

    protected List<Fahrer_Distanz> getDistances(List<Fahrer> fahrer, List<Integer> restaurantIDs)
    {
        List<Fahrer_Distanz> distanzen = new ArrayList<>();
        RestaurantAdressen = new ArrayList<>();
        RestaurantRepository restaurantRepository = new RestaurantRepository();

        for (Integer restaurant : restaurantIDs)
        {
            startAdresse = adressenRepository.getAdresseByCustomerId(restaurantRepository.findAnschriftIDFromRestaurant(restaurant));
            RestaurantAdressen.add(startAdresse);
            distanzen.addAll(getDistances(fahrer, restaurant));
        }

        return distanzen;
    }

    protected double getFahrzeit(int kundennummer)
    {
        Adressen kundenAdresse = adressenRepository.getAdresseByCustomerId(new KundeRepository().find("Kundennummer", kundennummer).firstResult().getAnschrift());

        double zeit = 0L;

        StringBuilder request = new StringBuilder();
        request.append("https://api.geoapify.com/v1/routing?waypoints=");

        for ( Adressen adresse : RestaurantAdressen)
        {
            request.append(adresse.getLat());
            request.append(",");
            request.append(adresse.getLng());
            request.append("|");
        }

        request.append(kundenAdresse.getLat());
        request.append(",");
        request.append(kundenAdresse.getLng());
        request.append("&mode=drive&apiKey=e15f70e37a39423cbe921dc88a1ded04");

        try
        {
            URL url = new URL(request.toString());
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(true);
            http.setRequestProperty("Content-Type", "application/json");

            JSONObject json = new JSONObject(Algo_FahrerAuswahl.getResponse(http.getInputStream()));

            zeit = json.getJSONArray("features").getJSONObject(0).getJSONObject("properties").getDouble("time");

            http.disconnect();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return zeit;
    }

    protected List<Fahrer_Distanz> getDistances(List<Fahrer> fahrer, int restaurant)
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
                distanzen.add(new Fahrer_Distanz(fahrer.get(i), restaurant,
                        info.getLong("distance"), info.getLong("time")));
            }

            http.disconnect();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return distanzen;
    }

}
