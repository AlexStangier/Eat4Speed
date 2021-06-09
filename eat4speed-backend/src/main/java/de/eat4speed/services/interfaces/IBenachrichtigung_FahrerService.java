package de.eat4speed.services.interfaces;

import de.eat4speed.dto.OrderDto;
import de.eat4speed.dto.PaymentDto;
import de.eat4speed.dto.StatisticDtoWrapper;
import de.eat4speed.entities.Bestellung;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

public interface IBenachrichtigung_FahrerService {

    List getAllBenachrichtigung_Fahrer_ungelesen(int fahrernummer);

    void markAsGelesen(int benachrichtigungs_ID);
}
