package de.eat4speed.services;

import de.eat4speed.entities.Status;
import de.eat4speed.repositories.StatusRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class StatusService {

    private StatusRepository _status;

    @Inject
    public StatusService(StatusRepository status) {
        this._status = status;
    }

}
