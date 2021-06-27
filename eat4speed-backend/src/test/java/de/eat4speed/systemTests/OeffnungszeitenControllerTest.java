package de.eat4speed.systemTests;

import de.eat4speed.controllers.OeffnungszeitenController;
import de.eat4speed.entities.Adressen;
import de.eat4speed.entities.Benutzer;
import de.eat4speed.entities.Oeffnungszeiten;
import de.eat4speed.entities.Restaurant;
import de.eat4speed.repositories.AdressenRepository;
import de.eat4speed.repositories.BenutzerRepository;
import de.eat4speed.repositories.OeffnungszeitenRepository;
import de.eat4speed.repositories.RestaurantRepository;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class OeffnungszeitenControllerTest {

    private static final String TEST_USER_NAME = "test-user";

    private int mockAddressId;
    private int mockRestaurantId;

    @TestHTTPEndpoint(OeffnungszeitenController.class)
    @TestHTTPResource("setArbeitstag")
    URL setArbeitstagEndpoint;

    @Inject
    BenutzerRepository _benutzerRepository;

    @Inject
    AdressenRepository _adressenRepository;

    @Inject
    RestaurantRepository _restaurantRepository;

    @Inject
    OeffnungszeitenRepository _oeffnungszeitenRepository;

    @BeforeEach
    void setup() {
        Benutzer mockRestaurantOwner = new Benutzer(TEST_USER_NAME, "Eugene", "Krabs",
                "eugene.krabs@krusty-krab.com", "9Qb0ebci0", "", "", "123");
        this._benutzerRepository.addBenutzer(mockRestaurantOwner);

        Adressen mockAddress = new Adressen("Musterstrasse", 23, "Musterstadt", 11233);
        this._adressenRepository.addAdresse(mockAddress);

        this.mockAddressId = mockAddress.getAdress_ID();

        Restaurant mockRestaurant = new Restaurant(mockRestaurantOwner.getBenutzer_ID(), "Krusty Krab",
                "", mockAddress.getAdress_ID(), 0.0, 1.0,
                Restaurant.VerificationStatus.NOT_VERIFIED.value());
        this._restaurantRepository.addRestaurant(mockRestaurant);

        this.mockRestaurantId = mockRestaurant.getRestaurant_ID();
    }

    @Test  // TST009 (1)
    @TestSecurity(authorizationEnabled = false)
    void trySetOpeningHours() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Timestamp open = new Timestamp(dateFormat.parse("12:00").getTime());
        Timestamp close = new Timestamp(dateFormat.parse("20:00").getTime());

        Oeffnungszeiten openingHours = new Oeffnungszeiten(open, close, Oeffnungszeiten.DayOfWeek.MONDAY.value(),
                this.mockRestaurantId);

        Jsonb jsonb = JsonbBuilder.create();

        int openingHoursId = given().contentType(ContentType.JSON)
                .body(jsonb.toJson(openingHours))
                .when().post(this.setArbeitstagEndpoint)
                .then().statusCode(201)
                .extract().path("oeffnungszeiten_ID");

        openingHours.setOeffnungszeiten_ID(openingHoursId);

        // Did the new entry make it into the database
        Oeffnungszeiten actualOpeningHours = this._oeffnungszeitenRepository.getOeffnungszeitenById(openingHoursId);
        Assertions.assertEquals(openingHours, actualOpeningHours);
    }

    @Test  // TST009 (2)
    @TestSecurity(authorizationEnabled = false)
    void trySetInvalidOpeningHours() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Timestamp open = new Timestamp(dateFormat.parse("20:00").getTime());
        Timestamp close = new Timestamp(dateFormat.parse("12:00").getTime());

        Oeffnungszeiten openingHours = new Oeffnungszeiten(open, close, Oeffnungszeiten.DayOfWeek.MONDAY.value(),
                this.mockRestaurantId);

        Jsonb jsonb = JsonbBuilder.create();

        given().contentType(ContentType.JSON)
                .body(jsonb.toJson(openingHours))
                .when().post(this.setArbeitstagEndpoint)
                .then().statusCode(400);
    }

    @AfterEach
    void cleanup() {
        this._restaurantRepository.deleteRestaurant(this.mockRestaurantId);
        this._adressenRepository.deleteAdresse(this.mockAddressId);
        this._benutzerRepository.deleteBenutzerByUsername(TEST_USER_NAME);
    }
}
