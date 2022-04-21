package com.edu.tks;

import com.edu.tks.model.RecordView;
import com.edu.tks.soap.SoapServiceApplication;
import io.restassured.RestAssured;
import io.restassured.internal.util.IOUtils;
import io.restassured.path.xml.element.Node;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;


import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SoapServiceApplication.class)
class RecordWebserviceTest {

    private void checkRecordResponse(Response response, String recordPath, String recordID, String title, String artist, String releaseDate, boolean rented) {
        assertEquals(recordID, response.getBody().xmlPath().getString("Envelope.Body." + recordPath + ".recordID"));
        assertEquals(title, response.getBody().xmlPath().getString("Envelope.Body." + recordPath + ".title"));
        assertEquals(artist, response.getBody().xmlPath().getString("Envelope.Body." + recordPath + ".artist"));
        assertEquals(releaseDate, response.getBody().xmlPath().getString("Envelope.Body." + recordPath + ".releaseDate"));
        assertEquals(rented ? "true" : "false", response.getBody().xmlPath().getString("Envelope.Body." + recordPath + ".rented"));
    }

    private void checkRecordResponse(Response response, String recordPath, String title, String artist, String releaseDate, boolean rented) {
        assertEquals(title, response.getBody().xmlPath().getString("Envelope.Body." + recordPath + ".title"));
        assertEquals(artist, response.getBody().xmlPath().getString("Envelope.Body." + recordPath + ".artist"));
        assertEquals(releaseDate, response.getBody().xmlPath().getString("Envelope.Body." + recordPath + ".releaseDate"));
        assertEquals(rented ? "true" : "false", response.getBody().xmlPath().getString("Envelope.Body." + recordPath + ".rented"));
    }

    private Response getRecords() {
        try {
            RestAssured.baseURI = BASE_PATH;
            FileInputStream fileInputStream = new FileInputStream(new File("requests/GetRecords.xml"));

            Response response = given()
                    .header("content-type", "text/xml")
                    .and()
                    .body(IOUtils.toByteArray(fileInputStream))
                    .when()
                    .post("/ws")
                    .then()
                    .statusCode(200)
                    .and()
                    .log().all()
                    .extract().response();

            return response;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int getRecordsLength() {
        return getRecords().xmlPath().getNodeChildren("Envelope.Body.getRecordsResponse.record").size();
    }

    private Response getRecordById(String recordID) {
        RestAssured.baseURI = BASE_PATH;
        String requestBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\""
                + " xmlns:gs=\"http://model.tks.edu.com/record\">"
                + "<soapenv:Header/>"
                + "<soapenv:Body>"
                + "<gs:getRecordByIdRequest>"
                + "<gs:id>" + recordID + "</gs:id>"
                + "</gs:getRecordByIdRequest>"
                + "</soapenv:Body>"
                + "</soapenv:Envelope>";

        Response response = given()
                .header("content-type", "text/xml")
                .and()
                .body(requestBody.getBytes(StandardCharsets.UTF_8))
                .when()
                .post("/ws")
                .then()
                .statusCode(200)
                .and()
                .log().all()
                .extract().response();

        return response;
    }

    private Node getRecordByName(String name) {
        Response response = getRecords();
        return response.getBody().xmlPath().getNode("Envelope.Body.getRecordsResponse.record[title='" + name +"']");
    }


    private final RecordView[] INIT_DATA = {
            new RecordView(UUID.fromString("02cf35bf-d025-440b-a6ec-17cc6c77b021"), "Moral Power", "Nothing but Lorde", "2020-03-04", false),
            new RecordView(UUID.fromString("3e3719e5-8689-4e65-883f-4cd06cae7195"), "Solar Panic", "Thieves", "2021-10-13", false)
    };

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    private String BASE_PATH;

    @PostConstruct
    private void setBasePath() {
        BASE_PATH = "http://localhost:" + port;
    }

    @Test
    public void getRecordById() {
        Response response = getRecordById("3e3719e5-8689-4e65-883f-4cd06cae7195");
        checkRecordResponse(response, "getRecordByIdResponse.record", "3e3719e5-8689-4e65-883f-4cd06cae7195", "Solar Panic", "Thieves", "2021-10-13", false);
    }

    @Test
    void getRecordsTests() {
        Response response = getRecords();
        checkRecordResponse(response, "getRecordsResponse.user[0]", "02cf35bf-d025-440b-a6ec-17cc6c77b021", "Moral Power", "Nothing but Lorde", "2020-03-04", false);
        checkRecordResponse(response, "getRecordsResponse.user[1]", "3e3719e5-8689-4e65-883f-4cd06cae7195", "Solar Panic", "Thieves", "2021-10-13", false);
    }

    @Test
    void addRecord() throws IOException {
        RestAssured.baseURI = BASE_PATH;
        FileInputStream fileInputStream = new FileInputStream(new File("requests/AddRecord.xml"));

        int len = getRecordsLength();
        Response response = given()
                .header("content-type", "text/xml")
                .and()
                .body(IOUtils.toByteArray(fileInputStream))
                .when()
                .post("/ws")
                .then()
                .statusCode(200)
                .and()
                .log().all()
                .extract().response();

        assertEquals(len + 1, getRecordsLength());
        checkRecordResponse(response, "addRecordResponse.record", "c1cf35bf-d025-440b-a6ec-17cc6c77b021", "SoapRecordTitle", "SoapRecordArtist", "2022-11-15", false);
    }

//    @Test
//    void removeRecord() throws IOException {
//        RestAssured.baseURI = BASE_PATH;
//        FileInputStream fileInputStream = new FileInputStream(new File("requests/RemoveRecord.xml"));
//
//        int len = getRecordsLength();
//        Response response = given()
//                .header("content-type", "text/xml")
//                .and()
//                .body(IOUtils.toByteArray(fileInputStream))
//                .when()
//                .post("/ws")
//                .then()
//                .statusCode(200)
//                .and()
//                .log().all()
//                .extract().response();
//
//        assertEquals(len + 1, getRecordsLength());
//        checkRecordResponse(response, "removeRecordResponse.record", "c1cf35bf-d025-440b-a6ec-17cc6c77b021", "SoapRecordTitle", "SoapRecordArtist", "2022-11-15", false);
//    }
//
//    @Test
//    void addAndRemoveRecord() {
//        String title = "title";
//        String artist = "artist";
//        String releaseDate = "2020-01-01";
//        RecordView newRecord = new RecordView(title, artist, releaseDate);
//        ResponseEntity<RecordView> response = rest.postForEntity(BASE_PATH + "/records", newRecord, RecordView.class);
//        assertTrue(response.getStatusCode().is2xxSuccessful());
//        assertEquals(title, response.getBody().getTitle());
//        assertEquals(artist, response.getBody().getArtist());
//        assertEquals(LocalDate.parse(releaseDate), response.getBody().getReleaseDate());
//        assertNotNull(response.getBody().getRecordID());
//        response = rest.exchange(BASE_PATH + "/records/" + response.getBody().getRecordID().toString(), HttpMethod.DELETE, null, RecordView.class);
//        assertTrue(response.getStatusCode().is2xxSuccessful());
//
//        ParameterizedTypeReference<List<RecordView>> typeRef = new ParameterizedTypeReference<>() {
//        };
//        ResponseEntity<List<RecordView>> all = rest.exchange(BASE_PATH + "/records", HttpMethod.GET, null, typeRef);
//        assertTrue(all.getStatusCode().is2xxSuccessful());
//        assertEquals(2, all.getBody().size());
//    }

//    @Test
//    void modifyRecord() {
//        String newTitle = "newTitle";
//        RecordView r = new RecordView(INIT_DATA[0].getRecordID(), newTitle, INIT_DATA[0].getArtist(), INIT_DATA[0].getReleaseDate().toString(), INIT_DATA[0].isRented());
//        HttpEntity<RecordView> ent = new HttpEntity<>(r);
//        ResponseEntity<RecordView> response = rest.exchange(BASE_PATH + "/records/" + INIT_DATA[0].getRecordID().toString(), HttpMethod.PUT, ent, RecordView.class);
//        assertTrue(response.getStatusCode().is2xxSuccessful());
//        RecordView responseRecord = rest.getForObject(BASE_PATH + "/records/" + INIT_DATA[0].getRecordID().toString(), RecordView.class);
//        assertEquals(r, responseRecord);
//        r.setTitle(INIT_DATA[0].getTitle());
//        response = rest.exchange(BASE_PATH + "/records/" + INIT_DATA[0].getRecordID().toString(), HttpMethod.PUT, ent, RecordView.class);
//        assertTrue(response.getStatusCode().is2xxSuccessful());
//    }
}