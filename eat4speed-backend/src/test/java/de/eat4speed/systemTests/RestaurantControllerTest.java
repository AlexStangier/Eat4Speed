package de.eat4speed.systemTests;

import de.eat4speed.controllers.RestaurantController;
import de.eat4speed.entities.Adressen;
import de.eat4speed.entities.Benutzer;
import de.eat4speed.entities.Restaurant;
import de.eat4speed.repositories.AdressenRepository;
import de.eat4speed.repositories.BenutzerRepository;
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

import static io.restassured.RestAssured.given;

@QuarkusTest
public class RestaurantControllerTest {

    private static final String TEST_USER_NAME = "test-user";

    private int mockAddressId;
    private int mockRestaurantId;

    @TestHTTPEndpoint(RestaurantController.class)
    @TestHTTPResource("updateRestaurantStammdaten")
    URL updateRestaurantStammdatenEndpoint;

    @Inject
    BenutzerRepository _benutzerRepository;

    @Inject
    AdressenRepository _adressenRepository;

    @Inject
    RestaurantRepository _restaurantRepository;

    /*
    @BeforeEach
    void setup() {
        Benutzer mockRestaurantOwner = new Benutzer(TEST_USER_NAME, "Eugene", "Krabs",
                "eugene.krabs@krusty-krab.com", "9Qb0ebci0", "", "", "123");
        this._benutzerRepository.addBenutzer(mockRestaurantOwner);

        Adressen mockAddress = new Adressen("Musterstrasse", Integer.toString(23), "Musterstadt", 11233);
        this._adressenRepository.addAdresse(mockAddress);

        this.mockAddressId = mockAddress.getAdress_ID();

        Restaurant mockRestaurant = new Restaurant(mockRestaurantOwner.getBenutzer_ID(), "Krusty Krab",
                "", mockAddress.getAdress_ID(), 0.0, 1.0,
                Restaurant.VerificationStatus.NOT_VERIFIED.value());
        this._restaurantRepository.addRestaurant(mockRestaurant);

        this.mockRestaurantId = mockRestaurant.getRestaurant_ID();
    }

    @Test  // TST010 (1)
    @TestSecurity(authorizationEnabled = false)
    void trySetOrderRadius() {
        Restaurant restaurant = _restaurantRepository.findByRestaurantnummer(this.mockRestaurantId);
        restaurant.setBestellradius(10);

        Jsonb jsonb = JsonbBuilder.create();

        given().contentType(ContentType.JSON)
                .body(jsonb.toJson(restaurant))
                .when().put(this.updateRestaurantStammdatenEndpoint)
                .then().statusCode(200);

        Restaurant actualRestaurant = _restaurantRepository.findByRestaurantnummer(this.mockRestaurantId);
        Assertions.assertEquals(restaurant, actualRestaurant);
    }

    @Test  // TST010 (2)
    @TestSecurity(authorizationEnabled = false)
    void trySetInvalidOrderRadius() {
        Restaurant restaurant = _restaurantRepository.findByRestaurantnummer(this.mockRestaurantId);
        restaurant.setBestellradius(-1);

        Jsonb jsonb = JsonbBuilder.create();

        given().contentType(ContentType.JSON)
                .body(jsonb.toJson(restaurant))
                .when().put(this.updateRestaurantStammdatenEndpoint)
                .then().statusCode(400);
    }

    @Test  // TST011 (1)
    @TestSecurity(authorizationEnabled = false)
    void trySetMinimumOrderValue() {
        Restaurant restaurant = _restaurantRepository.findByRestaurantnummer(this.mockRestaurantId);
        restaurant.setMindestbestellwert(20);

        Jsonb jsonb = JsonbBuilder.create();

        given().contentType(ContentType.JSON)
                .body(jsonb.toJson(restaurant))
                .when().put(this.updateRestaurantStammdatenEndpoint)
                .then().statusCode(200);

        Restaurant actualRestaurant = _restaurantRepository.findByRestaurantnummer(this.mockRestaurantId);
        Assertions.assertEquals(restaurant, actualRestaurant);
    }

    @Test  // TST011 (2)
    @TestSecurity(authorizationEnabled = false)
    void trySetInvalidMinimumOrderValue() {
        Restaurant restaurant = _restaurantRepository.findByRestaurantnummer(this.mockRestaurantId);
        restaurant.setMindestbestellwert(-1);

        Jsonb jsonb = JsonbBuilder.create();

        given().contentType(ContentType.JSON)
                .body(jsonb.toJson(restaurant))
                .when().put(this.updateRestaurantStammdatenEndpoint)
                .then().statusCode(400);
    }

    @AfterEach
    void cleanup() {
        this._restaurantRepository.deleteRestaurant(this.mockRestaurantId);
        this._adressenRepository.deleteAdresse(this.mockAddressId);
        this._benutzerRepository.deleteBenutzerByUsername(TEST_USER_NAME);
    }
    */
}
