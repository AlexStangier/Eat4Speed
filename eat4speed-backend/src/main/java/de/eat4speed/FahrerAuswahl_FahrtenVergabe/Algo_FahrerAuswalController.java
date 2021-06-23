package de.eat4speed.FahrerAuswahl_FahrtenVergabe;

import de.eat4speed.entities.Bestellung;
import de.eat4speed.repositories.BestellungRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Path("/FahrerAuswahl")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Algo_FahrerAuswalController {

    @Inject
    Algo_FahrerAuswahl _Fahrer_Auswahl;

    @PUT
    @Path("{Start_ID}")
    public void FahrerAuswahl(@PathParam("Start_ID") int id)
    {
        _Fahrer_Auswahl = new Algo_FahrerAuswahl();
        _Fahrer_Auswahl.Fahrtenvergabe(id);
    }

    @PUT
    @Path("start/{Auftrag_ID}")
    public void startFahrerAuswahl(@PathParam("Auftrag_ID") int Auftrag_ID)
    {
        BestellungRepository bestellungRepository = new BestellungRepository();
        List<Bestellung> bestellungen = bestellungRepository.find("Auftrags_ID", Auftrag_ID).list();

        int count = 0;
        for (Bestellung b : bestellungen)
        {
            if (b.getStatus().equals("abholbereit") | b.getStatus().equals("storniert"))
            {
                count++;
            }
        }

        if (count == bestellungen.size())
        {
            try {
                URL url = new URL("http://localhost:1337/FahrerAuswahl/" + Auftrag_ID);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("PUT");
                http.setDoOutput(false);
                http.setReadTimeout(10);
                http.getInputStream();
                http.disconnect();
            } catch (Exception e) {
            }
        }
    }
}