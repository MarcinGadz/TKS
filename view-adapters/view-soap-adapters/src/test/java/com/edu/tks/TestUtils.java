package com.edu.tks;

import com.edu.tks.clients.UserClient;
import io.restassured.response.Response;
import org.springframework.context.annotation.Bean;
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
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("com.edu.tks.model.user");
        return marshaller;
    }
}
