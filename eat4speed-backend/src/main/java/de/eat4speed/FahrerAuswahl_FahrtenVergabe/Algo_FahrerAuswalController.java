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

    @POST
    @Path("start/{Bestell_ID}")
    public void startFahrerAuswahl(@PathParam("Bestell_ID") int Bestell_ID)
    {
        BestellungRepository bestellungRepository = new BestellungRepository();
        List<Bestellung> bestellungen = bestellungRepository.find("Auftrags_ID", bestellungRepository.getBestellungByID(Bestell_ID).getAuftrags_ID()).list();
        System.out.println(bestellungen);
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
                //URL url = new URL("http://localhost:1337/FahrerAuswahl/" + bestellungen.get(0).getAuftrags_ID());
                URL url = new URL("https://eat4speed.xyz/FahrerAuswahl/" + bestellungen.get(0).getAuftrags_ID());
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("PUT");
                http.setDoOutput(false);
                http.setReadTimeout(200);
                http.getInputStream();
                http.disconnect();
            } catch (Exception e) {
            }
        }
    }
}
