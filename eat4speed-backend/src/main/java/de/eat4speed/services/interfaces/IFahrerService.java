package de.eat4speed.services.interfaces;

import de.eat4speed.entities.Fahrer;
import de.eat4speed.entities.Fahrzeug;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public interface IFahrerService {

    Response addFahrer(Fahrer obj);

    Response setPause(int pause, int fahrernummer);

    Object getAmountInPause();

    List getFahrerByID(int id);

    List<String> getAllFahrer();

    List<String> getNotVerifiedFahrer();

    List<String> getVerifiedFahrer();

    Response updateFahrer_Fahrzeug_Id(int id, Fahrzeug fahrzeug);

    Response updateFahrer_Verifiziert(int id);

    Response deleteFahrer(int id);

    Response updateFahrer_anzahl_aktueller_Auftraege(int fahrernummer, int anzahl);

    ArrayList<String> get_Fahrer_Fzg_Pos(String email);

    ArrayList<String> get_Restaurant_Lng_Lat(String email);

    ArrayList<String> get_Kunde_Lng_Lat(String email);

    void set_auftrags_zeit(long auftr_id, java.sql.Timestamp dt);

    void set_Bestellung_abgeholt(String rest_name, int auftr_id);

    void set_Fahrer_aktuellePos_Abholung(String restaurant_name, String email);

    void set_Fahrer_aktuellePos_Ablieferung(long auftr_id, String email);

    public void set_Bestellung_abgeliefert(int auftr_id);

    public void accident_report_bestellunng(long auftrags_id);

    public void accident_report_fahrer(long auftrags_id);
}
