package de.eat4speed.services.interfaces;

import de.eat4speed.dto.OrderDto;
import de.eat4speed.dto.PaymentDto;
import de.eat4speed.dto.StatisticDtoWrapper;
import de.eat4speed.entities.Bestellung;
import de.eat4speed.entities.Gericht;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

public interface IBestellungService {

    Response addBestellung(Bestellung bestellung);

    /**
     * Creates and persists an order with all attached items
     *
     * @param obj Dto containing a list of items and an customer identifier
     * @return HTTP Response
     */
    Response createBestellung(OrderDto obj) throws SQLException;

    /**
     * Updates Auftragsstatus to 'bezahlt'
     * @param AuftragsId auftrag to be updated
     * @return success or error
     */
    PaymentDto payForOrder(Integer AuftragsId) throws SQLException;


    /**
     * Creates a statistic for all previous orders
     * @param restaurantId
     * @param startTime
     * @param endTime
     * @return
     * @throws SQLException
     */
    StatisticDtoWrapper getStatistic(Long restaurantId, long startTime, long endTime) throws SQLException;

    /**
     * Returns the amount of Gericht orders by customer
     * @param customerId
     * @param gerichtId
     * @return
     */
    Integer getAmountOrdersByCustomerIdAndGerichtId(int customerId, int gerichtId);

    /**
     * Returns the amount of All Orders from a restaurant by customer
     * @param restaurantId
     * @param customerId
     * @return
     */
    Integer getAllOrdersForRestaurantIdByCustomerID(int restaurantId, int customerId);

    List getRestaurantBestellungen(String email);

    Response updateBestellungStatus(Bestellung bestellung);

    List getProduktUndAnzahl(int id);

    List getKundeBestellungen(String status, String email);

}
