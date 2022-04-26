package com.edu.tks;

import io.restassured.response.Response;

import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;

public class TestUtils {

    public static final String ENVELOPE = "Envelope.Body.";

    public  static String pack(String namespace, String content) {
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"" +
                " xmlns:gs=\"http://model.tks.edu.com/" + namespace + "\">" +
                "<soapenv:Header/>" +
                "<soapenv:Body>" +
                content +
                "</soapenv:Body>" +
                "</soapenv:Envelope>";
    }

    public static Response sendRequest(String requestPayload) {
        return given()
                .header("content-type", "text/xml")
                .and()
                .body(requestPayload.getBytes(StandardCharsets.UTF_8))
                .when()
                .post("/ws");
    }
}
