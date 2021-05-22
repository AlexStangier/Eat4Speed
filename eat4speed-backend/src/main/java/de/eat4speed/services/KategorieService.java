package de.eat4speed.services;

import de.eat4speed.entities.Kategorie;
import de.eat4speed.repositories.KategorieRepository;
import de.eat4speed.services.interfaces.IKategorieService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class KategorieService implements IKategorieService {

    private KategorieRepository _kategorie;

    @Inject
    public KategorieService(KategorieRepository kategorie) {
        this._kategorie = kategorie;
    }

    @Override
    public Response addKategorie(Kategorie kategorie)
    {
        _kategorie.addKategorie(kategorie);

        return Response.status(Response.Status.CREATED).entity(kategorie).build();
    }

    @Override
    public List listAll()
    {
        return _kategorie.listAllKategories();
    }
}
