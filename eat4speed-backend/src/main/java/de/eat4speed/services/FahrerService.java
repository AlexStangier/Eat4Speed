package de.eat4speed.services;

import de.eat4speed.entities.Fahrer;
import de.eat4speed.entities.Fahrzeug;
import de.eat4speed.repositories.FahrerRepository;
import de.eat4speed.services.interfaces.IFahrerService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FahrerService implements IFahrerService {

    private FahrerRepository fahrerRepository;

    @Inject
    public FahrerService(FahrerRepository fahrerRepository) {
        this.fahrerRepository = fahrerRepository;
    }

    @Override
    public Response addFahrer(Fahrer fahrer)
    {
        fahrerRepository.addFahrer(fahrer);

        return Response.status(Response.Status.CREATED).entity(fahrer).build();
    }

    @Override
    public Response setPause(int pause, int fahrernummer)
    {
        fahrerRepository.setPause(pause, fahrernummer);

        return Response.status(Response.Status.OK).entity(pause).entity(fahrernummer).build();
    }

    @Override
    public Object getAmountInPause() {
        return fahrerRepository.getAmountInPause();
    }

    @Override
    public List getFahrerByID(int benutzer_Id) {
        return fahrerRepository.getFahrer(benutzer_Id);
    }

    @Override
    public List getAllFahrer()
    {
        return fahrerRepository.getAllFahrer();
    }

    @Override
    public List getNotVerifiedFahrer()
    {
        return fahrerRepository.getNotVerifiedFahrer();
    }

    @Override
    public List getVerifiedFahrer()
    {
        return fahrerRepository.getVerifiedFahrer();
    }

    @Override
    public Response updateFahrer_Fahrzeug_Id(int id, Fahrzeug fahrzeug)
    {
        fahrerRepository.updateFahrer_Fahrzeug_id(id, fahrzeug.getFahrzeug_ID());

        return Response.status(Response.Status.OK).entity(fahrzeug).build();
    }

    @Override
    public Response updateFahrer_Verifiziert(int id)
    {
        fahrerRepository.updateFahrer_Verifiziert(id);

        return Response.status(Response.Status.OK).build();
    }

    @Override
    public Response deleteFahrer(int id)
    {
        fahrerRepository.deleteFahrer(id);

        return Response.status(Response.Status.OK).build();
    }

    @Override
    public Response updateFahrer_anzahl_aktueller_Auftraege(int fahrernummer, int anzahl) {
        fahrerRepository.updateFahrer_anzahl_aktueller_Auftraege(fahrernummer, anzahl);
        return Response.status(Response.Status.OK).build();
    }

    @Override
    public ArrayList<String> get_Fahrer_Fzg_Pos(String email) {
        return fahrerRepository.get_Fahrer_Fzg_Pos(email);
    }

    @Override
    public ArrayList<String> get_Restaurant_Lng_Lat(String email) {
        return fahrerRepository.get_Restautant_Lng_Lat(email);
    }

    @Override
    public ArrayList<String> get_Kunde_Lng_Lat(String email) {
        return fahrerRepository.get_Kunde_Lng_Lat(email);
    }

    @Override
    public void set_auftrags_zeit(long auftr_id, java.sql.Timestamp dt){ fahrerRepository.set_auftrags_zeit(auftr_id, dt);}

    @Override
    public void set_Bestellung_abgeholt(String rest_name, int auftr_id){ fahrerRepository.set_Bestellung_abgeholt(rest_name, auftr_id);}

    @Override
    public void set_Fahrer_aktuellePos_Abholung(String restaurant_name, String email){ fahrerRepository.set_Fahrer_aktuellePos_Abholung(restaurant_name, email);}

    @Override
    public void set_Fahrer_aktuellePos_Ablieferung(long auftrags_id, String email){ fahrerRepository.set_Fahrer_aktuellePos_Ablieferung(auftrags_id, email);}

    @Override
    public void set_Bestellung_abgeliefert(int auftr_id){ fahrerRepository.set_Bestellung_abgeliefert(auftr_id);}

    @Override
    public void accident_report_bestellunng(long auftrags_id){fahrerRepository.accident_report_bestellunng(auftrags_id);}

    @Override
    public void accident_report_fahrer(long auftrags_id){fahrerRepository.accident_report_fahrer(auftrags_id);}


}
