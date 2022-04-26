package com.edu.tks;

import com.edu.tks.model.rental.RentalSOAP;
import com.edu.tks.soap.SoapServiceApplication;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

import static com.edu.tks.TestUtils.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SoapServiceApplication.class)
class RentalSoapServiceTest {

    private static List<RentalSOAP> testData;

    @LocalServerPort
    private int port;

    private String BASE_PATH;

    @PostConstruct
    private void init() {
        BASE_PATH = "http://localhost:" + port;
    }


    @Test
    public void testGetAllRentals() {
        RestAssured.baseURI = BASE_PATH;

        String requestPayload = pack("rental", "<gs:getAllRentalsRequest/>");

        System.out.println(requestPayload);

        sendRequest(requestPayload)
                .then()
                .log().all()
                .statusCode(200)
                .rootPath(ENVELOPE + "getAllRentalsResponse")
                .assertThat()
                .body("rental.rentalID", hasItems(
                        "418d0406-e040-408a-abf3-7788db83b889",
                        "53d3b654-c542-11ec-9d64-0242ac120002"
                ))
                .body("rental.clientID", hasItems(
                        "02cf35bf-d025-440b-a6ec-17cc6c77b021",
                        "3e3719e5-8689-4e65-883f-4cd06cae7195"
                ))
                .body("rental.recordID", hasItems(
                        "cebbee82-2398-4dc2-a94d-a4c863286ff0",
                        "c6f3fbf7-135b-498e-8154-3a5b9d291145"
                ))
                .body("rental.rentDate", hasItems(
                        "2022-05-03",
                        "2022-01-12"
                ))
                .body("rental.expectedReturnDate", hasItems(
                        "2022-05-10",
                        "2022-01-19"
                ))
                .body("rental.active", hasItems(
                        "true",
                        "true"
                ));
    }

    @Test
    public void testGetAllArchiveRentals() {
        RestAssured.baseURI = BASE_PATH;

        String requestPayload = pack("rental", "<gs:getAllArchiveRentalsRequest/>");

        sendRequest(requestPayload)
                .then()
                .log().all()
                .statusCode(200)
                .rootPath(ENVELOPE + "getAllArchiveRentalsResponse")
                .assertThat()
                .body("rental.rentalID", equalTo("1fa7cdf2-2b0c-428b-bbf7-a593d56e3b74"))
                .body("rental.clientID", equalTo("3e3719e5-8689-4e65-883f-4cd06cae7195"))
                .body("rental.recordID", equalTo("c6f3fbf7-135b-498e-8154-3a5b9d291145"))
                .body("rental.rentDate", equalTo("2021-02-13"))
                .body("rental.expectedReturnDate", equalTo("2021-02-20"))
                .body("rental.actualReturnDate", equalTo("2021-02-20"))
                .body("rental.active", equalTo("false"));
    }

    @Test
    public void returnAndRentTheSameRecord() {
        RestAssured.baseURI = BASE_PATH;

        String returnPayload = pack("rental", "<gs:returnRecordRequest>" +
                "<gs:rentalID>418d0406-e040-408a-abf3-7788db83b889</gs:rentalID>" +
                "<gs:userID>02cf35bf-d025-440b-a6ec-17cc6c77b021</gs:userID>" +
                "</gs:returnRecordRequest>");

        sendRequest(returnPayload)
                .then()
                .log().all()
                .statusCode(200)
                .rootPath(ENVELOPE + "returnRecordResponse")
                .assertThat()
                .body("rental.rentalID", equalTo("418d0406-e040-408a-abf3-7788db83b889"))
                .body("rental.clientID", equalTo("02cf35bf-d025-440b-a6ec-17cc6c77b021"))
                .body("rental.recordID", equalTo("cebbee82-2398-4dc2-a94d-a4c863286ff0"))
                .body("rental.rentDate", equalTo("2022-05-03"))
                .body("rental.expectedReturnDate", equalTo("2022-05-10"))
                .body("rental.actualReturnDate", equalTo(LocalDate.now().toString()))
                .body("rental.active", equalTo("false"));

        String rentPayload = pack("rental", "<gs:rentRecordRequest>" +
                "<gs:recordID>02cf35bf-d025-440b-a6ec-17cc6c77b021</gs:recordID>" +
                "<gs:userID>cebbee82-2398-4dc2-a94d-a4c863286ff0</gs:userID>" +
                "</gs:rentRecordRequest>");

        sendRequest(rentPayload)
                .then()
                .log().all()
                .statusCode(200)
                .rootPath(ENVELOPE + "rentRecordResponse")
                .assertThat()
                .body("rental.clientID", equalTo("02cf35bf-d025-440b-a6ec-17cc6c77b021"))
                .body("rental.recordID", equalTo("cebbee82-2398-4dc2-a94d-a4c863286ff0"))
                .body("rental.rentDate", equalTo(LocalDate.now().toString()))
                .body("rental.expectedReturnDate", equalTo(LocalDate.now().plusDays(7).toString()))
                .body("rental.active", equalTo("true"));
    }
}
