package de.eat4speed.services.interfaces;

import de.eat4speed.entities.Fahrer;
import de.eat4speed.entities.Fahrzeug;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public interface IFahrerService {

    Response addFahrer(Fahrer obj);

    List<String> getAllFahrer();

    List<String> getNotVerifiedFahrer();

    List<String> getVerifiedFahrer();

    Response updateFahrer_Fahrzeug_Id(int id, Fahrzeug fahrzeug);

    Response updateFahrer_Verifiziert(int id);

    Response deleteFahrer(int id);


    ArrayList<String> get_Fahrer_Fzg_Pos(String email);

    ArrayList<String> get_Restaurant_Lng_Lat(String email);

    ArrayList<String> get_Kunde_Lng_Lat(String email);

    void set_auftrags_zeit(long auftr_id, java.sql.Timestamp dt);
}
