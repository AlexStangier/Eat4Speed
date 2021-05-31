package de.eat4speed.services.interfaces;

import de.eat4speed.dto.PaypalDto;

public interface IPaypalService {

    /**
     * Tries to checkout an order identified by its Auftrags Id
     * @param auftragId
     * @return if succesful returns the auftrags id else -1
     */
    PaypalDto checkOutViaPaypal(long auftragId);

}
