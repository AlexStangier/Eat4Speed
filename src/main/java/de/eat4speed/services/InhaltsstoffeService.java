package de.eat4speed.services;

import de.eat4speed.entities.Benutzer;
import de.eat4speed.entities.Inhaltsstoffe;
import de.eat4speed.repositories.InhaltsstoffeRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class InhaltsstoffeService {

    private InhaltsstoffeRepository _inhaltsstoffe;

    @Inject
    public InhaltsstoffeService(InhaltsstoffeRepository inhaltsstoffe) {
        this._inhaltsstoffe = inhaltsstoffe;
    }

}
