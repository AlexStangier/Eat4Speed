package de.eat4speed.systemTests;

import de.eat4speed.controllers.BenutzerController;
import de.eat4speed.entities.Benutzer;
import de.eat4speed.repositories.BenutzerRepository;
import de.eat4speed.services.interfaces.IBenutzerService;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.net.URL;

@Transactional
@QuarkusTest
public class BenutzerControllerTest {

    private static BenutzerRepository _benutzerRepository;

    @Inject
    public BenutzerControllerTest(BenutzerRepository benutzer) {
        this._benutzerRepository = benutzer;
    }

    @TestHTTPEndpoint(BenutzerController.class)
    @TestHTTPResource("Benutzer/add")
    URL benutzerEndpointAdd;

    @TestHTTPEndpoint(BenutzerController.class)
    @TestHTTPResource("Benutzer/get")
    URL benutzerEndpointGet;

    @Inject
    IBenutzerService _benutzerService;

    Benutzer dummyBenutzer = new Benutzer("deleteMe", "Test", "Test", "Test", "Test", "", "Test", 123);

    /*  TST001  */

    @Test
    void tryRegisterAsUser() {
        dummyBenutzer.setRolle("user");
        Assertions.assertTrue(_benutzerService.addBenutzer(dummyBenutzer).getStatusInfo().equals(Response.Status.CREATED));
    }

    /*@Test
    void tryRegisterAsUserWhichAlreadyExists() {
        dummyBenutzer.setRolle("user");
        Response.StatusType res = _benutzerService.addBenutzer(dummyBenutzer).getStatusInfo();
        System.out.println(res);
        Assertions.assertTrue(_benutzerService.addBenutzer(dummyBenutzer).getStatusInfo().equals(Response.Status.CREATED));
    }*/

    @Test
    void tryRegisterAsRestaurant() {
        dummyBenutzer.setRolle("restaurant");
        Assertions.assertTrue(_benutzerService.addBenutzer(dummyBenutzer).getStatusInfo().equals(Response.Status.CREATED));
    }

    @Test
    void tryRegisterAsAdmin() {
        dummyBenutzer.setRolle("admin");
        Assertions.assertTrue(_benutzerService.addBenutzer(dummyBenutzer).getStatusInfo().equals(Response.Status.CREATED));
    }

    @AfterAll
    static void deleteAllUsersFromTest(){
        _benutzerRepository.deleteBenutzerByUsername("deleteMe");
    }

    //https://www.baeldung.com/java-quarkus-testing
}
