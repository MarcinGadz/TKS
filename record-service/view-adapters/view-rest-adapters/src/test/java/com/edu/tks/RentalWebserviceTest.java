package com.edu.tks;

import com.edu.tks.rest.exception.InputExceptionView;
import com.edu.tks.rest.model.RentalView;
import com.edu.tks.rest.RestServiceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RestServiceApplication.class)
class RentalWebserviceTest {

    private final RentalView[] INIT_DATA = {
            new RentalView(
                    UUID.fromString("418d0406-e040-408a-abf3-7788db83b889"),
                    "cebbee82-2398-4dc2-a94d-a4c863286ff0",
                    "02cf35bf-d025-440b-a6ec-17cc6c77b021",
                    LocalDateTime.of(2022, 5, 3, 13, 36),
                    LocalDateTime.of(2022, 5, 10, 13, 36),
                    null,
                    true
            ),
            new RentalView(
                    UUID.fromString("53d3b654-c542-11ec-9d64-0242ac120002"),
                    "c6f3fbf7-135b-498e-8154-3a5b9d291145",
                    "3e3719e5-8689-4e65-883f-4cd06cae7195",
                    LocalDateTime.of(2022, 1, 12, 15, 23),
                    LocalDateTime.of(2022, 1,19, 15, 23),
                    null,
                    true
            ),
    };

    private final RentalView[] INIT_DATA_ARCHIVED = {
            new RentalView(
                    UUID.fromString("1fa7cdf2-2b0c-428b-bbf7-a593d56e3b74"),
                    "c6f3fbf7-135b-498e-8154-3a5b9d291145",
                    "c6f3fbf7-135b-498e-8154-3a5b9d291145",
                    LocalDateTime.of(2021, 2, 13, 16, 13),
                    LocalDateTime.of(2021, 2, 20, 16, 13),
                    LocalDateTime.of(2021, 2, 20, 15, 45),
                    false
            ),
    };

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    private String BASE_PATH;

    RentalWebserviceTest() throws InputExceptionView {
    }

    @PostConstruct
    private void setBasePath() {
        BASE_PATH = "http://localhost:" + port + "/clients/";
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
        assertTrue(body.size() == INIT_DATA_ARCHIVED.length);
        for (int i = 0; i < INIT_DATA_ARCHIVED.length; i++) {
            assertEquals(INIT_DATA_ARCHIVED[i], body.get(i));
        }
    }

    @Test
    void shouldAddRental() {
        String userId = INIT_DATA[0].getClientID();
        String recordId = "3e3719e5-8689-4e65-883f-4cd06cae7195";

        RentalView newRental;
        try {
            newRental = new RentalView(userId, recordId);
        } catch (InputExceptionView e) {
            throw new RuntimeException(e);
        }

        ResponseEntity<RentalView> res = rest.postForEntity(BASE_PATH + userId + "/rent", newRental, RentalView.class);
        assertTrue(res.getStatusCode().is2xxSuccessful());
        RentalView resRental = res.getBody();
        assertEquals(resRental.getRecordID(), recordId);
        assertEquals(resRental.getClientID(), userId);
        assertNotNull(resRental.getRentalID());
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
        assertEquals(2, body.size());
        assertEquals(INIT_DATA[0].getRentalID(), body.get(1).getRentalID());
    }
}
