package de.eat4speed.systemTests;

import de.eat4speed.controllers.GerichtBilderController;
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
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class GerichtControllerTest {

    private static final String TEST_USER_NAME = "test-user";

    private int dummyAddressId;
    private int dummyRestaurantId;
    private int dummyGerichtId;

    @TestHTTPEndpoint(GerichtController.class)
    @TestHTTPResource("updateGerichtAllData")
    URL updateGerichtEndpoint;

    @TestHTTPEndpoint(GerichtBilderController.class)
    @TestHTTPResource("upload")
    URL uploadGerichtPictureEndpoint;

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
        Benutzer dummyRestaurantOwner = new Benutzer(TEST_USER_NAME, "Eugene", "Krabs",
                "eugene.krabs@krusty-krab.com", "9Qb0ebci0", "", "", 0);
        this._benutzerRepository.addBenutzer(dummyRestaurantOwner);

        Adressen dummyAddress = new Adressen("Musterstrasse", 23, "Musterstadt", 11233);
        this._adressenRepository.addAdresse(dummyAddress);

        this.dummyAddressId = dummyAddress.getAdress_ID();

        Restaurant dummyRestaurant = new Restaurant(dummyRestaurantOwner.getBenutzer_ID(), "Krusty Krab",
                "", dummyAddress.getAdress_ID(), 5.0, 1.0,
                Restaurant.VerificationStatus.NOT_VERIFIED.value());
        this._restaurantRepository.addRestaurant(dummyRestaurant);

        this.dummyRestaurantId = dummyRestaurant.getRestaurant_ID();

        Gericht dummyGericht = new Gericht(dummyRestaurantId, "Krabby Patty", "",
                "", 0.0, Gericht.AvailabilityStatus.AVAILABLE.value(), Gericht.MealType.MEAL.value());
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

    @Test  // TST008 (1)
    void tryUpdateGerichtPicture() throws IOException, NoSuchAlgorithmException {
        Gericht gericht = _gerichtRepository.getGerichtByGerichtID(this.dummyGerichtId);
        String pictureFilename = "Bild" + gericht.getGericht_ID();

        File picture = new File("src/test/resources/220px-Krabby_Patty.png");
        InputStream pictureStream = new FileInputStream(picture);

        given().contentType("multipart/form-data")
                .multiPart("file", pictureFilename, pictureStream)
                .multiPart("fileName", pictureFilename)
                .when().post(this.uploadGerichtPictureEndpoint)
                .then().statusCode(200);

        File actualPicture = new File(
                "src/main/resources/Bilder/gerichtBilder/Bild" + gericht.getGericht_ID() + ".png");
        InputStream actualPictureStream = new FileInputStream(actualPicture);

        MessageDigest md5 = MessageDigest.getInstance("MD5");

        pictureStream = new FileInputStream(picture);
        byte[] pictureDigest = md5.digest(IOUtils.toByteArray(pictureStream));
        md5.reset();
        byte[] actualPictureDigest = md5.digest(IOUtils.toByteArray(actualPictureStream));

        Assertions.assertTrue(MessageDigest.isEqual(pictureDigest, actualPictureDigest));

        if (!actualPicture.delete()) {
            throw new IOException("Could not delete picture after finishing test!");
        }
    }

    @Test  // TST008 (2)
    void tryUpdateGerichtPictureWithInvalidData() throws FileNotFoundException {
        Gericht gericht = _gerichtRepository.getGerichtByGerichtID(this.dummyGerichtId);
        String pictureFilename = "Bild" + gericht.getGericht_ID();

        File picture = new File("src/test/resources/meal-image-test.html");
        InputStream pictureStream = new FileInputStream(picture);

        given().contentType("multipart/form-data")
                .multiPart("file", pictureFilename, pictureStream)
                .multiPart("fileName", pictureFilename)
                .when().post(this.uploadGerichtPictureEndpoint)
                .then().statusCode(400);
    }

    @AfterEach
    void cleanup() {
        this._gerichtRepository.deleteGericht(this.dummyGerichtId);
        this._restaurantRepository.deleteRestaurant(this.dummyRestaurantId);
        this._adressenRepository.deleteAdresse(this.dummyAddressId);
        this._benutzerRepository.deleteBenutzerByUsername(TEST_USER_NAME);
    }
}
