package de.eat4speed.systemTests;

import de.eat4speed.controllers.BenutzerController;
import de.eat4speed.entities.Benutzer;
import de.eat4speed.repositories.BenutzerRepository;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class BenutzerControllerTest {

    private static final String TEST_USER_NAME = "test-user";

    @TestHTTPEndpoint(BenutzerController.class)
    @TestHTTPResource("register")
    URL registerBenutzerEndpoint;

    @Inject
    BenutzerRepository _benutzerRepository;


    @Test  // TST001 (1)
    @TestSecurity(authorizationEnabled = false)
    void tryRegisterAsUser() {
        Benutzer mockUser = new Benutzer(TEST_USER_NAME, "Test", "Test", "Test",
                "Test", Benutzer.UserRole.KUNDE.toString(), "Test", "123");

        Jsonb jsonb = JsonbBuilder.create();

        int userId = given().contentType(ContentType.JSON)
                .body(jsonb.toJson(mockUser))
                .when().post(this.registerBenutzerEndpoint)
                .then().statusCode(201)
                .extract().path("benutzer_ID");

        // Check if the new user entry made it into the database
        Benutzer actualUser = _benutzerRepository.getBenutzerByID(userId);
        Assertions.assertNotNull(actualUser);
    }

    @Test  // TST001 (1)
    @TestSecurity(authorizationEnabled = false)
    void tryRegisterAsRestaurant() {
        Benutzer mockUser = new Benutzer(TEST_USER_NAME, "Test", "Test", "Test",
                "Test", Benutzer.UserRole.RESTAURANT.toString(), "Test", "123");

        Jsonb jsonb = JsonbBuilder.create();

        int userId = given().contentType(ContentType.JSON)
                .body(jsonb.toJson(mockUser))
                .when().post(this.registerBenutzerEndpoint)
                .then().statusCode(201)
                .extract().path("benutzer_ID");

        // Check if the new user entry made it into the database
        Benutzer actualUser = _benutzerRepository.getBenutzerByID(userId);
        Assertions.assertNotNull(actualUser);
    }

    @Test  // TST001 (1)
    @TestSecurity(authorizationEnabled = false)
    void tryRegisterAsAdmin() {
        Benutzer mockUser = new Benutzer(TEST_USER_NAME, "Test", "Test", "Test",
                "Test", Benutzer.UserRole.ADMIN.toString(), "Test", "123");

        Jsonb jsonb = JsonbBuilder.create();

        int userId = given().contentType(ContentType.JSON)
                .body(jsonb.toJson(mockUser))
                .when().post(this.registerBenutzerEndpoint)
                .then().statusCode(201)
                .extract().path("benutzer_ID");

        // Check if the new user entry made it into the database
        Benutzer actualUser = _benutzerRepository.getBenutzerByID(userId);
        Assertions.assertNotNull(actualUser);
    }

    /*
    @Test  // TST001 (2)
    @TestSecurity(authorizationEnabled = false)
    void tryRegisterAsUserWhichAlreadyExists() {
        Benutzer mockUser = new Benutzer(TEST_USER_NAME, "Test", "Test", "Test",
                "Test", Benutzer.UserRole.KUNDE.toString(), "Test", "123");
        mockUser.setPasswort(Base64.getEncoder().encodeToString(mockUser.getPasswort().getBytes(StandardCharsets.UTF_8)));
        _benutzerRepository.addBenutzer(mockUser);

        Jsonb jsonb = JsonbBuilder.create();

        given().contentType(ContentType.JSON)
                .body(jsonb.toJson(mockUser))
                .when().post(this.registerBenutzerEndpoint)
                .then().statusCode(400);
    }

    @Test  // TST001 (3)
    @TestSecurity(authorizationEnabled = false)
    void tryRegisterAsUserWithIncompleteInfo() {
        Benutzer mockUser = new Benutzer(TEST_USER_NAME, "Test", "Test", "",
                "Test", Benutzer.UserRole.KUNDE.toString(), "", "123");

        Jsonb jsonb = JsonbBuilder.create();

        given().contentType(ContentType.JSON)
                .body(jsonb.toJson(mockUser))
                .when().post(this.registerBenutzerEndpoint)
                .then().statusCode(400);
    }

    @Test  // TST001 (3)
    @TestSecurity(authorizationEnabled = false)
    void tryRegisterAsUserWithInvalidRole() {
        Benutzer mockUser = new Benutzer(TEST_USER_NAME, "Test", "Test", "Test",
                "Test", "invalid", "Test", "123");

        Jsonb jsonb = JsonbBuilder.create();

        given().contentType(ContentType.JSON)
                .body(jsonb.toJson(mockUser))
                .when().post(this.registerBenutzerEndpoint)
                .then().statusCode(400);
    }
    */

    @AfterEach
    void cleanup() {
        this._benutzerRepository.deleteBenutzerByUsername(TEST_USER_NAME);
    }

    //https://www.baeldung.com/java-quarkus-testing
}
