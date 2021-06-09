package de.eat4speed.services.interfaces;

import de.eat4speed.entities.Bestellzuordnung;

import javax.ws.rs.core.Response;

public interface IBestellzuordnungService {

    Response addBestellzuordnung(Bestellzuordnung bestellzuordnung);

    Response deleteBestellzuordnung(int id);
}
