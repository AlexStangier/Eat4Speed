package de.eat4speed.services;

import java.io.*;

import de.eat4speed.services.interfaces.IRoutingService;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class RoutingService implements IRoutingService {

    @Inject
    FahrerService _fahrer;

    /**
     * Aufbau Fahrer_Mode -> fahrzeugart, lng, lat, kapazität
     * Aufbau Restaurant_location -> Auftrag_ID, lng, lat, Restaurant_name
     * Aufbau Kunde_location -> Auftrag_ID, lng, lat, Kunde: strasse, hausnummer, plz, ort
     */

    public JSONObject add_shipment(String id, double src_lng, double src_lat, double dest_lng, double dest_lat) {


        return new JSONObject()
                .put("id", id).put("pickup", new JSONObject()
                        .put("location", new JSONArray()
                                .put(src_lng).put(src_lat))
                        .put("duration", 300)
                )
                .put("delivery", new JSONObject()
                        .put("location", new JSONArray()
                                .put(dest_lng).put(dest_lat))
                        .put("duration", 300)
                );

    }


    /**
     * create_Request
     * <p>
     * Creates http-Request-Body for GEOAPIFY, using data from database
     *
     * @param email, type String
     * @return Request object, type JSONObject
     */
    public JSONObject create_Request(String email) {

        // Get data from database
        //ArrayList<String> fahrer = _fahrer.get_Fahrer_Fzg_Pos(email);
        //ArrayList<String> restaurants = _fahrer.get_Restaurant_Lng_Lat(email);
        //ArrayList<String> kunden = _fahrer.get_Kunde_Lng_Lat(email);

        ArrayList<String> fahrer = new ArrayList<>();
        fahrer.add("drive");
        fahrer.add("7.866770631041064");
        fahrer.add("48.03079725");
        fahrer.add("30");

        ArrayList<String> restaurants = new ArrayList<>();
        restaurants.add("1");
        restaurants.add("7.810987275508813");
        restaurants.add("47.9808561");
        restaurants.add("Pizza Bude");
        restaurants.add("1");
        restaurants.add("7.713063727757731");
        restaurants.add("47.9994133");
        restaurants.add("Ultra Pizza");
        restaurants.add("4");
        restaurants.add("7.713063727757731");
        restaurants.add("47.9994133");
        restaurants.add("Ultra Pizza");
        restaurants.add("5");
        restaurants.add("7.713063727757731");
        restaurants.add("47.9994133");
        restaurants.add("Ultra Pizza");

        ArrayList<String> kunden = new ArrayList<>();
        kunden.add("1");
        kunden.add("7.721710049788417");
        kunden.add("48.027207849999996");
        kunden.add("Am Hohberg");
        kunden.add("14");
        kunden.add("79112");
        kunden.add("Freiburg");
        kunden.add("4");
        kunden.add("7.721710049788417");
        kunden.add("48.027207849999996");
        kunden.add("Am Hohberg");
        kunden.add("14");
        kunden.add("79112");
        kunden.add("Freiburg");
        kunden.add("5");
        kunden.add("7.8170875");
        kunden.add("48.0169446");
        kunden.add("Libellenweg");
        kunden.add("16");
        kunden.add("79110");
        kunden.add("Freiburg");


        System.out.println(fahrer);
        System.out.println(restaurants);
        System.out.println(kunden);


        JSONArray shipments = new JSONArray();
        int kunden_list_pointer = 0;

        // Create shipments from restaurant(s) to customer(s)
        for (int restaurant_list_pointer = 0; restaurant_list_pointer < restaurants.size() / 4; restaurant_list_pointer++) {
            if (restaurant_list_pointer > 0 && !restaurants.get(restaurant_list_pointer * 4).equals(restaurants.get((restaurant_list_pointer - 1) * 4))) {
                kunden_list_pointer++;
            }
            shipments.put(add_shipment(restaurants.get(restaurant_list_pointer * 4) + "[" + restaurants.get(restaurant_list_pointer * 4 + 3) + "|" + kunden.get(kunden_list_pointer * 7 + 3) + " " + kunden.get(kunden_list_pointer * 7 + 4) + ", " + kunden.get(kunden_list_pointer * 7 + 5) + " " + kunden.get(kunden_list_pointer * 7 + 6) + "]", Double.parseDouble(restaurants.get(restaurant_list_pointer * 4 + 1)), Double.parseDouble(restaurants.get(restaurant_list_pointer * 4 + 2)), Double.parseDouble(kunden.get(kunden_list_pointer * 7 + 1)), Double.parseDouble(kunden.get(kunden_list_pointer * 7 + 2))));
        }

        // Retun full request body
        return new JSONObject()
                .put("mode", fahrer.get(0))
                .put("agents", new JSONArray()
                        .put(new JSONObject()
                                .put("start_location", new JSONArray()
                                        .put(fahrer.get(1)).put(fahrer.get(2))
                                )
                                .put("pickup_capacity", fahrer.get(3))
                        )

                ).put("shipments", shipments);
    }

    @Default
    @Override
    public JSONArray get_best_Route() throws Exception {

        URL url = new URL("https://api.geoapify.com/v1/routeplanner?apiKey=e15f70e37a39423cbe921dc88a1ded04");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/json");

        String data = create_Request("arturs@arturs.de").toString();

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

        String output = textBuilder.toString();


        JSONObject props = new JSONObject(output).getJSONArray("features").getJSONObject(0).getJSONObject("properties");
        JSONArray waypoints = props.getJSONArray("waypoints");
        JSONArray legs = props.getJSONArray("legs");
        //JSONArray actions = props.getJSONArray("actions");
        JSONArray list_waypoints = new JSONArray();

        for (int i = 0; i < legs.length(); i++) {
            String beschreibung;
            StringBuilder restaurant = new StringBuilder();
            StringBuilder kunde = new StringBuilder();
            StringBuilder auftrags_id_string = new StringBuilder();
            ArrayList<String> auftrags_id = new ArrayList<>();


            for (int j = 0; j < waypoints.getJSONObject(legs.getJSONObject(i).getInt("to_waypoint_index")).getJSONArray("actions").length(); j++) {
                if (!auftrags_id.contains(StringUtils.substringBefore(waypoints.getJSONObject(legs.getJSONObject(i).getInt("to_waypoint_index")).getJSONArray("actions").getJSONObject(j).getString("shipment_id"), "["))) {
                    auftrags_id.add(StringUtils.substringBefore(waypoints.getJSONObject(legs.getJSONObject(i).getInt("to_waypoint_index")).getJSONArray("actions").getJSONObject(j).getString("shipment_id"), "["));
                }

            }
            for (int auftrags_id_counter = 0; auftrags_id_counter < auftrags_id.size(); auftrags_id_counter++) {

                if (auftrags_id_counter == (auftrags_id.size() - 1)) {
                    auftrags_id_string.append(auftrags_id.get(auftrags_id_counter));
                } else {
                    auftrags_id_string.append(auftrags_id.get(auftrags_id_counter)).append(", ");
                }
            }
            if (waypoints.getJSONObject(legs.getJSONObject(i).getInt("to_waypoint_index")).getJSONArray("actions").getJSONObject(0).get("type").equals("delivery")) {
                beschreibung = "Lieferung";
                kunde.append(StringUtils.substringBetween(waypoints.getJSONObject(legs.getJSONObject(i).getInt("to_waypoint_index")).getJSONArray("actions").getJSONObject(0).get("shipment_id").toString(), "|", "]"));
                restaurant.append("--- ");
            } else if (waypoints.getJSONObject(legs.getJSONObject(i).getInt("to_waypoint_index")).getJSONArray("actions").getJSONObject(0).get("type").equals("pickup")) {
                beschreibung = "Abholung";
                restaurant.append(StringUtils.substringBetween(waypoints.getJSONObject(legs.getJSONObject(i).getInt("to_waypoint_index")).getJSONArray("actions").getJSONObject(0).get("shipment_id").toString(), "[", "|"));
                kunde.append("--- ");
            } else {
                beschreibung = "No Data";
            }
            // Add one leg to data list
            list_waypoints.put(new JSONObject()
                    .put("count_stations", waypoints.length())
                    .put("auftrags_id", auftrags_id_string.toString())
                    .put("station", i + 1)
                    .put("beschreibung", beschreibung)
                    .put("restaurantname", restaurant)
                    .put("kunde", kunde)
                    .put("entfernung", (Double.parseDouble(legs.getJSONObject(i).get("distance").toString())/1000.0) + "km")
                    .put("start", new JSONObject().put("lat", waypoints.getJSONObject(legs.getJSONObject(i).getInt("from_waypoint_index")).getJSONArray("original_location").get(1))
                            .put("lng", waypoints.getJSONObject(legs.getJSONObject(i).getInt("from_waypoint_index")).getJSONArray("original_location").get(0)))
                    .put("end", new JSONObject().put("lat", waypoints.getJSONObject(legs.getJSONObject(i).getInt("to_waypoint_index")).getJSONArray("original_location").get(1))
                            .put("lng", waypoints.getJSONObject(legs.getJSONObject(i).getInt("to_waypoint_index")).getJSONArray("original_location").get(0)))
            );
        }
        return list_waypoints;
    }
}
