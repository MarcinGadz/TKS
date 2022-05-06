package com.edu.tks;

import io.restassured.response.Response;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

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

    public static Jaxb2Marshaller getUserMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.edu.tks.model.user");
        return marshaller;
    }

    public static Jaxb2Marshaller getRecordMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.edu.tks.model.record");
        return marshaller;
    }

    public static Jaxb2Marshaller getRentalMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.edu.tks.model.rental");
        return marshaller;
    }
}
