package de.eat4speed.services.interfaces;

import de.eat4speed.entities.Gericht_Allergene;

import javax.ws.rs.core.Response;

public interface IGericht_AllergeneService {

    Response addGericht_Allergene(Gericht_Allergene gericht_allergene);
}
