package com.edu.tks;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.PermissionException;
import com.edu.tks.model.RecordView;
import com.edu.tks.model.RentalView;
import com.edu.tks.model.UserTypeView;
import com.edu.tks.rest.RestServiceApplication;
import com.edu.tks.model.UserView;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RestServiceApplication.class)
class RentWebserviceTest {

    private final RentalView[] INIT_DATA = {new RentalView(

            UUID.fromString("418d0406-e040-408a-abf3-7788db83b889"),
            new UserView(UUID.fromString("cebbee82-2398-4dc2-a94d-a4c863286ff0"), "Eleanora", true, UserTypeView.CLIENT),
            new RecordView(UUID.fromString("02cf35bf-d025-440b-a6ec-17cc6c77b021"), "Moral Power", "Nothing but Lorde", "2020-03-04", false))};

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    private String BASE_PATH;

    RentWebserviceTest() throws PermissionException, InputException {
    }

    @PostConstruct
    private void setBasePath() {
        BASE_PATH = "http://localhost:" + port + "/record-shop/users/";
    }

    @Test
    void allRentals() {
        String targetUrl = BASE_PATH + "rentals/";

        ParameterizedTypeReference<List<RentalView>> typeRef = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<List<RentalView>> response = rest.exchange(targetUrl, HttpMethod.GET, null, typeRef);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        List<RentalView> body = response.getBody();
        assertNotNull(body);
        assertEquals(INIT_DATA.length, body.size());
        for (int i = 0; i < INIT_DATA.length; i++) {
            assertEquals(INIT_DATA[i], body.get(i));
        }
    }

    @Test
    void allArchiveRentals() {
        String targetUrl = BASE_PATH + "archiveRentals/";

        ParameterizedTypeReference<List<RentalView>> typeRef = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<List<RentalView>> response = rest.exchange(targetUrl, HttpMethod.GET, null, typeRef);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        List<RentalView> body = response.getBody();
        assertTrue(body.isEmpty());
    }

    @Test
    void shouldAddRental() throws InputException {
        UserView newUser = new UserView();
        newUser.setUserId(UUID.fromString(INIT_DATA[0].getClientID()));

        RecordView newRec = new RecordView();
        newRec.setRecordID("3e3719e5-8689-4e65-883f-4cd06cae7195");
        RentalView newRental = new RentalView(newUser, newRec);

        ResponseEntity<RentalView> res = rest.postForEntity(BASE_PATH + newUser.getUserID().toString() + "/rent", newRental, RentalView.class);
        assertTrue(res.getStatusCode().is2xxSuccessful());
        assertEquals(res.getBody().getRecordID(), newRec.getRecordID().toString());
        assertEquals(res.getBody().getClientID(), newUser.getUserID().toString());
        assertNotNull(res.getBody().getRentalID());
    }

    @Test
    void shouldArchiveRental() {

        ResponseEntity<RentalView> res = rest.postForEntity(BASE_PATH + INIT_DATA[0].getClientID() + "/return", INIT_DATA[0], RentalView.class);
        assertTrue(res.getStatusCode().is2xxSuccessful());

        ParameterizedTypeReference<List<RentalView>> typeRef = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<List<RentalView>> response = rest.exchange(BASE_PATH + "archiveRentals", HttpMethod.GET, null, typeRef);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        List<RentalView> body = response.getBody();
        assertEquals(1, body.size());
        assertEquals(INIT_DATA[0].getRentalID(), body.get(0).getRentalID());
    }
}