package de.eat4speed.services;

import java.io.*;

import de.eat4speed.services.interfaces.IRoutingService;
import de.eat4speed.services.interfaces.IFahrerService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class RoutingService implements IRoutingService {

    @Inject
    FahrerService _fahrer;

    @Override
    @Default
    public JSONObject db_test() {

        ArrayList<String> results = (ArrayList<String>) _fahrer.get_Fahrer_Fzg_Pos();
        ArrayList<String> results1 = (ArrayList<String>) _fahrer.get_Restaurant_Lng_Lat();
        ArrayList<String> results2 = (ArrayList<String>) _fahrer.get_Kunde_Lng_Lat();
        System.out.println(results);
        System.out.println(results1);
        System.out.println(results2);


        JSONObject info = new JSONObject().put("mode", results.get(0)).put("start_location", new JSONArray().put(results.get(1)).put(results.get(2)));
        return info;
    }

    public JSONObject create_Request_Body(){
        ArrayList<String> mode_and_driver = (ArrayList<String>) _fahrer.get_Fahrer_Fzg_Pos();
        ArrayList<String> restaurant_koords = (ArrayList<String>) _fahrer.get_Restaurant_Lng_Lat();
        ArrayList<String> kunde_koords = (ArrayList<String>) _fahrer.get_Kunde_Lng_Lat();

        return null;

    }

    public JSONObject add_shipment(String id, double src_lng, double src_lat, double dest_lng, double dest_lat, boolean assigned) {

        JSONObject shipment = new JSONObject()
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


        return shipment;

    }

    public JSONObject add_mode_and_agent(String mode, double start_long, double start_lat, double end_long, double end_lat, int capacity) {

        JSONObject mode_agent = new JSONObject()
                .put("mode", mode)
                .put("agents", new JSONArray()
                        .put(new JSONObject()
                                .put("start_location", new JSONArray()
                                        .put(start_long).put(start_lat)
                                )
                                .put("end_location", new JSONArray()
                                        .put(end_long).put(end_lat)
                                )
                                .put("pickup_capacity", capacity)
                        )
                );


        return mode_agent;

    }


    @Override
    @Default
    public JSONObject get_best_Route() throws Exception {


        URL url = new URL("https://api.geoapify.com/v1/routeplanner?apiKey=e15f70e37a39423cbe921dc88a1ded04");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/json");

        JSONArray shipments = new JSONArray();

        // Add or remove "shipment"
        shipments.put(add_shipment("order_2", 7.862707, 48.014258, 7.857937, 48.011836, false));
        shipments.put(add_shipment("order_3", 7.862707, 48.014258, 7.840162, 48.018656, false));
        shipments.put(add_shipment("order_1", 7.819171, 48.017708, 7.823581, 48.011250, false));
        shipments.put(add_shipment("order_0", 7.827899, 48.010338, 7.830039, 48.009247, false));

        JSONObject request = add_mode_and_agent("drive", 7.845290, 47.993264, 7.840162, 48.018656, 10).put("shipments", shipments);


        String data = request.toString();

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);

        InputStream ips = http.getInputStream();

        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(
                new InputStreamReader(ips, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }

        String output = textBuilder.toString();

        JSONObject props = new JSONObject(output).getJSONArray("features").getJSONObject(0).getJSONObject("properties");

        JSONArray waypoints = props.getJSONArray("waypoints");
        JSONArray actions = props.getJSONArray("actions");
        JSONArray list_of_sorted_waypoints = new JSONArray();

        for (int i = 0; i < actions.length(); i++) {
            list_of_sorted_waypoints.put(waypoints.getJSONObject(actions.getJSONObject(i).getInt("waypoint_index")).get("original_location"));
        }

        JSONObject response = new JSONObject().put("sorted_waypoints", list_of_sorted_waypoints);


        return response;
    }
}
