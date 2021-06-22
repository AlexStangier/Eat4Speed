package de.eat4speed.services.interfaces;

import de.eat4speed.entities.Auftrag;
import de.eat4speed.entities.Gericht;

import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.List;

public interface IAuftragService {

    Response addAuftrag(Auftrag auftrag);

    Response deleteAuftag(int id);

    Response updateAuftragStatus(int id, String status);
    Response updateAuftragFahrernummer(int auftrags_ID, int fahrernummer);

    List getAuftragFahrernummerByAuftrags_ID(int id);

    Response setToErledigt(int id);
}
