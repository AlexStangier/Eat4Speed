package de.eat4speed.services;

import de.eat4speed.entities.Urlaub;
import de.eat4speed.repositories.UrlaubRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class UrlaubService {

    private UrlaubRepository _urlaub;

    @Inject
    public UrlaubService(UrlaubRepository urlaub) {
        this._urlaub = urlaub;
    }

}
