package com.edu.tks;

import com.edu.tks.model.record.RecordSOAP;
import com.edu.tks.soap.SoapServiceApplication;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;


import javax.annotation.PostConstruct;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SoapServiceApplication.class)
class RecordWebserviceTest {

    private static List<RecordSOAP> testData;

    @LocalServerPort
    private int port;

    private String BASE_PATH;
    private final String ENVELOPE = "Envelope.Body.";

    @PostConstruct
    private void init() {
        BASE_PATH = "http://localhost:" + port;
        testData = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            RecordSOAP newRecord = new RecordSOAP();
            newRecord.setRecordID("02cf35bf-d025-441b-a6ec-17cc6c68b02" + i);
            newRecord.setArtist("TestRecordArtistN" + i);
            newRecord.setTitle("TestRecordTitleN" + i);
            newRecord.setRented(false);
            try {
                XMLGregorianCalendar releaseDate = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
                        2000 + i,
                        i%12 + 1,
                        1%30 + 1,
                        DatatypeConstants.FIELD_UNDEFINED
                );
                newRecord.setReleaseDate(releaseDate);
            } catch (DatatypeConfigurationException e) {
                throw new RuntimeException(e);
            }
            testData.add(newRecord);
        }
    }

    private String pack(String content) {
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"" +
                " xmlns:gs=\"http://model.tks.edu.com/record\">" +
                "<soapenv:Header/>" +
                "<soapenv:Body>" +
                content +
                "</soapenv:Body>" +
                "</soapenv:Envelope>";
    }

    private Response sendRequest(String requestPayload) {
        return given()
                .header("content-type", "text/xml")
                .and()
                .body(requestPayload.getBytes(StandardCharsets.UTF_8))
                .when()
                .post("/ws");
    }

    @Test
    public void getRecordById() {
        RestAssured.baseURI = BASE_PATH;

        String recordID = "02cf35bf-d025-440b-a6ec-17cc6c77b021";

        String requestPayload = pack("<gs:getRecordByIdRequest>" +
                "<gs:id>" + recordID + "</gs:id>" +
                "</gs:getRecordByIdRequest>");

        sendRequest(requestPayload)
                .then()
                .log().all()
                .statusCode(200)
                .and()
                .rootPath(ENVELOPE + " getRecordByIdResponse")
                .body("record.recordID", equalTo(recordID));
    }

    @Test
    void getRecordsTests() {
        RestAssured.baseURI = BASE_PATH;

        String requestPayload = pack("<gs:getRecordsRequest/>");

        sendRequest(requestPayload)
                .then()
                .log().all()
                .statusCode(200)
                .and()
                .rootPath(ENVELOPE + " getRecordsResponse")
                .body("record.recordID", hasItems(
                        "02cf35bf-d025-440b-a6ec-17cc6c77b021",
                        "3e3719e5-8689-4e65-883f-4cd06cae7195")
                );
    }

    @Test
    void addAndRemoveRecord() {
        RestAssured.baseURI = BASE_PATH;

        RecordSOAP record = testData.get(0);

        String addPayload = pack("<gs:addRecordRequest>" +
            "<gs:record>" +
            "<gs:recordID>" + record.getRecordID() + "</gs:recordID>" +
            "<gs:title>" + record.getTitle() + "</gs:title>" +
            "<gs:artist>" + record.getArtist() + "</gs:artist>" +
            "<gs:releaseDate>" + record.getReleaseDate().toString() + "</gs:releaseDate>" +
            "<gs:rented>" + record.isRented() + "</gs:rented>" +
            "</gs:record>" +
            "</gs:addRecordRequest>"
        );

        String addedID = sendRequest(addPayload)
                .then()
                .log().all()
                .statusCode(200)
                .rootPath(ENVELOPE + "addRecordResponse")
                .body("record.title", equalTo(record.getTitle()))
                .body("record.artist", equalTo(record.getArtist()))
                .body("record.releaseDate", equalTo(record.getReleaseDate().toString()))
                .body("record.rented", equalTo(Boolean.toString(record.isRented())))
                .extract().body().xmlPath().setRootPath(ENVELOPE + "addRecordResponse").getString("record.recordID");

        String removePayload = pack("<gs:removeRecordRequest>" +
                "<gs:recordID>" + addedID + "</gs:recordID>" +
                "</gs:removeRecordRequest>"
        );

        sendRequest(removePayload)
                .then()
                .log().all()
                .statusCode(200)
                .rootPath(ENVELOPE + "removeRecordResponse")
                .body("record.recordID", equalTo(addedID));
    }
}