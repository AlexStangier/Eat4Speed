package de.eat4speed.services.interfaces;

import org.json.JSONArray;
import org.json.JSONObject;


public interface IRoutingService {

    //public JSONObject add_shipment(String id, double src_lng, double src_lat, double dest_lng, double dest_lat, boolean assigned);

    //public JSONObject add_mode_and_agent(String mode, double start_long, double start_lat, double end_long, double end_lat, int capacity);

    JSONArray get_best_Route(String email) throws Exception;

    JSONObject db_test();

}