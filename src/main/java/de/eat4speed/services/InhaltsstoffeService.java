package de.eat4speed.services;


import de.eat4speed.repositories.InhaltsstoffeRepository;
import de.eat4speed.services.interfaces.IInhaltsstoffeService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class InhaltsstoffeService implements IInhaltsstoffeService {

    private InhaltsstoffeRepository _inhaltsstoffe;

    @Inject
    public InhaltsstoffeService(InhaltsstoffeRepository inhaltsstoffe) {
        this._inhaltsstoffe = inhaltsstoffe;
    }


    @Override
    public Response addInhaltsstoffe(Inhaltsstoffe obj) {
        _inhaltsstoffe.addInhaltsstoffe(obj);
        return Response.status(Response.Status.CREATED).entity(obj).build();
    }

    @Override
    public List listAll() {
        return _inhaltsstoffe.listAllInhaltsstoffnamen();
    }


}
