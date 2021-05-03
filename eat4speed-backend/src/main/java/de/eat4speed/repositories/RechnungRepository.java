package de.eat4speed.repositories;


import de.eat4speed.entities.Rechnung;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class RechnungRepository implements PanacheRepository<Rechnung> {



}