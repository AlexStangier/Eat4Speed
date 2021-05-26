package de.eat4speed.systemTests;

import de.eat4speed.controllers.GerichtController;
import de.eat4speed.entities.Adressen;
import de.eat4speed.entities.Benutzer;
import de.eat4speed.entities.Gericht;
import de.eat4speed.entities.Restaurant;
import de.eat4speed.repositories.AdressenRepository;
import de.eat4speed.repositories.BenutzerRepository;
import de.eat4speed.repositories.GerichtRepository;
import de.eat4speed.repositories.RestaurantRepository;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;
import java.net.URL;

import static io.restassured.RestAssured.given;

@Transactional
@QuarkusTest
public class GerichtControllerTest {

    private int dummyAddressId;
    private int dummyRestaurantId;
    private int dummyGerichtId;

    @TestHTTPEndpoint(GerichtController.class)
    @TestHTTPResource("updateGerichtAllData")
    URL updateGerichtEndpoint;

    @Inject
    BenutzerRepository _benutzerRepository;

    @Inject
    AdressenRepository _adressenRepository;

    @Inject
    RestaurantRepository _restaurantRepository;

    @Inject
    GerichtRepository _gerichtRepository;

    @BeforeEach
    void setup() {
        Benutzer dummyRestaurantOwner = new Benutzer("test-user", "Eugene", "Krabs",
                "eugene.krabs@krusty-krab.com", "9Qb0ebci0", "", "", 0);
        this._benutzerRepository.addBenutzer(dummyRestaurantOwner);

        Adressen dummyAddress = new Adressen("Musterstrasse", 23, "Musterstadt", 11233);
        this._adressenRepository.addAdresse(dummyAddress);

        this.dummyAddressId = dummyAddress.getAdress_ID();

        Restaurant dummyRestaurant = new Restaurant(dummyRestaurantOwner.getBenutzer_ID(), "Krusty Krab",
                "", dummyAddress.getAdress_ID(), 0.0, 0.0, (byte) 0);
        this._restaurantRepository.addRestaurant(dummyRestaurant);

        this.dummyRestaurantId = dummyRestaurant.getRestaurant_ID();

        Gericht dummyGericht = new Gericht(dummyRestaurantId, "Krabby Patty", "",
                "", 0.0, (byte) 1, (byte) 0);
        this._gerichtRepository.addGericht(dummyGericht);

        this.dummyGerichtId = dummyGericht.getGericht_ID();
    }

    @Test  // TST007 (1)
    void trySetGerichtToNotAvailable() {
        Gericht gericht = _gerichtRepository.getGerichtByGerichtID(this.dummyGerichtId);
        gericht.setVerfuegbar((byte) 0);

        Jsonb jsonb = JsonbBuilder.create();

        given().contentType(ContentType.JSON)
                .body(jsonb.toJson(gericht))
                .when().put(this.updateGerichtEndpoint)
                .then().statusCode(200);
    }

    @Test  // TST007 (3)
    void trySetGerichtAvailabilityToInvalidStatus() {
        Gericht gericht = _gerichtRepository.getGerichtByGerichtID(this.dummyGerichtId);
        gericht.setVerfuegbar((byte) 23);

        Jsonb jsonb = JsonbBuilder.create();

        given().contentType(ContentType.JSON)
                .body(jsonb.toJson(gericht))
                .when().put(this.updateGerichtEndpoint)
                .then().statusCode(400);
    }

    @AfterEach
    void cleanup() {
        this._gerichtRepository.deleteGericht(this.dummyGerichtId);
        this._restaurantRepository.deleteRestaurant(this.dummyRestaurantId);
        this._adressenRepository.deleteAdresse(this.dummyAddressId);
        this._benutzerRepository.deleteBenutzerByUsername("test-user");
    }
}
