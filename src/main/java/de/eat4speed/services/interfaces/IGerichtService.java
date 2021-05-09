package de.eat4speed.services.interfaces;

import de.eat4speed.dishAlternatives.DishAlternativesOptions;
import de.eat4speed.entities.Gericht;
import de.eat4speed.multipart.MultipartBody;

import javax.ws.rs.core.Response;
import java.util.List;

public interface IGerichtService {

    Response addGericht(Gericht gericht);

    Gericht getGerichtByGerichtID(int gericht_ID);

    List getAllGerichteDataRestaurantSpeisekarte(int restaurant_ID);

    List getAllGetraenkeDataRestaurantSpeiseKarte(int restaurant_ID);

    List getGerichtDataByGerichtName(String gerichtName);

    List getGerichtDataByGerichtKategorie(String kategorie);

    List getGerichtDataByRestaurant_ID(int restaurant_ID);

    List getGerichtDataByGericht_ID(int gericht_ID);

    List getGerichtAlternatives(DishAlternativesOptions options);

    List getGerichtDataByKundennummer_Favoriten(int kundennummer);

    Response updateGerichtAllData(Gericht gericht);

    void updatePicturePath(String path, int id);

    Response deleteGericht(int gericht_ID);
}
