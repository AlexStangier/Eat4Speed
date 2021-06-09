package de.eat4speed.services.interfaces;

import de.eat4speed.entities.Bewertung;

import javax.ws.rs.core.Response;
import java.util.List;

public interface IBewertungService {

    Response addOrUpdateBewertung(Bewertung bewertung);

    List getBewertungDataByKundennummerAndRestaurant_ID(int kundennummer,int restaurant_ID);

    List getBewertungDataByRestaurant_ID(int restaurant_ID);

    List getAverageBewertungAndCountBewertungByRestaurant_ID(int restaurant_ID);

}