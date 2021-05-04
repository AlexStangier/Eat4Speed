package de.eat4speed.services.interfaces;

import de.eat4speed.entities.Bestellung;
import de.eat4speed.entities.Gericht;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

public interface IBestellungService {

    Response addBestellung(Bestellung bestellung);

    /**
     * Creates and persists an order
     * @param items the order itmes
     * @param customerId the customer
     * @return
     */
    Response createBestellung(int[] items, long customerId) throws SQLException;
}
