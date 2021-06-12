package de.eat4speed.services;

import de.eat4speed.entities.Fahrer;
import de.eat4speed.entities.Fahrzeug;
import de.eat4speed.repositories.FahrerRepository;
import de.eat4speed.services.interfaces.IFahrerService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
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
    public List get_Fahrer_Fzg_Pos() {
        return fahrerRepository.get_Fahrer_Fzg_Pos();
    }

    @Override
    public List get_Restaurant_Lng_Lat() {
        return fahrerRepository.get_Restautant_Lng_Lat();
    }

    @Override
    public List get_Kunde_Lng_Lat() {
        return fahrerRepository.get_Kunde_Lng_Lat();
    }


}
