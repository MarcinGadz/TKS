package com.edu.tks;

import com.edu.tks.model.user.UserSOAP;
import com.edu.tks.model.user.UserTypeSOAP;
import com.edu.tks.soap.SoapServiceApplication;
import com.edu.tks.soap.UserSoapService;
import com.edu.tks.soap.WebServiceConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.ws.test.server.RequestCreators;
import org.springframework.ws.test.server.ResponseMatchers;
import org.springframework.xml.transform.StringSource;

import javax.annotation.PostConstruct;
import javax.xml.transform.Source;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SoapServiceApplication.class)
class UserSoapServiceTest {

    private static List<UserSOAP> testData;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    private String BASE_PATH;
    private String ENVELOPE = "Envelope.Body.";

    @PostConstruct
    private void setBasePath() {
        BASE_PATH = "http://localhost:" + port;
        testData = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            UserSOAP newUser = new UserSOAP();
            newUser.setUserID("cebbee82-2398-4dc2-a94d-a4c863287ff" + i);
            newUser.setLogin("NewTestUserN" + 1);
            newUser.setType(i%2 == 0 ? UserTypeSOAP.CLIENT : UserTypeSOAP.ADMINISTRATOR);
            newUser.setActive(true);
            testData.add(newUser);
        }
    }

    private String pack(String content) {
        String out = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"" +
            " xmlns:gs=\"http://model.tks.edu.com/user\">" +
            "<soapenv:Header/>" +
            "<soapenv:Body>" +
            content +
            "</soapenv:Body>" +
            "</soapenv:Envelope>";
        return  out;
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
    public void testGetUserById() {
        RestAssured.baseURI = BASE_PATH;

        String requestPayload = pack("<gs:getUserByIDRequest>" +
                "<gs:userID>cebbee82-2398-4dc2-a94d-a4c863286ff0</gs:userID>" +
                "</gs:getUserByIDRequest>");

        System.out.println(requestPayload);

        String responseName = "";

        sendRequest(requestPayload)
                .then()
                .log().all()
                .and()
                .statusCode(200)
                .and()
                .rootPath(ENVELOPE + "getUserByIdResponse")
                .and()
                .assertThat()
                .body("user.userID", equalTo("cebbee82-2398-4dc2-a94d-a4c863286ff0"));
    }

    @Test
    public void testGetUsers() {
        RestAssured.baseURI = BASE_PATH;

        String requestPayload = pack("<gs:getUsersRequest/>");

        System.out.println(requestPayload);

        sendRequest(requestPayload)
                .then()
                .log().all()
                .and()
                .statusCode(200)
                .and()
                .rootPath(ENVELOPE + "getUsersResponse")
                .and()
                .and()
                .assertThat()
                .body("user.userID", hasItems(
                        "cebbee82-2398-4dc2-a94d-a4c863286ff0",
                        "bbe979ba-ed9c-4028-8c18-97740c25ae99",
                        "c6f3fbf7-135b-498e-8154-3a5b9d291145",
                        "f6abb370-5dbb-4473-bf8d-505bdcf6ccce",
                        "73d2c12b-179e-4700-879d-8e3de56fe55f",
                        "bfac0aaa-e7d2-4e33-9fa9-8111b14bcd58"
                        ));
    }

    @Test
    public void testAddAndRemoveUser() {
        RestAssured.baseURI = BASE_PATH;

        UserSOAP userToAdd = testData.get(0);

        String addPayload = pack("<gs:addUserRequest>" +
                "<gs:user>" +
                "<gs:userID>" + userToAdd.getUserID() + "</gs:userID>" +
                "<gs:login>" + userToAdd.getLogin() + "</gs:login>" +
                "<gs:type>" + userToAdd.getType().toString() + "</gs:type>" +
                "<gs:active>" + userToAdd.isActive() + "</gs:active>" +
                "</gs:user>" +
                "</gs:addUserRequest>");

        String addedID = sendRequest(addPayload)
                .then()
                .log().all()
                .and()
                .statusCode(200)
                .and()
                .rootPath(ENVELOPE + "addUserResponse")
                .body("user.login", equalTo(userToAdd.getLogin()))
                .body("user.type", equalTo(userToAdd.getType().value()))
                .body("user.active", equalTo(Boolean.toString(userToAdd.isActive())))
                .extract()
                .body()
                .xmlPath()
                .setRootPath(ENVELOPE + "addUserResponse")
                .getString("user.userID");

        System.out.println("Added ID" + addedID);

        String removePayload = pack("<gs:deleteUserRequest>" +
                "<gs:userID>" + addedID + "</gs:userID>" +
                "</gs:deleteUserRequest>");

        sendRequest(removePayload)
                .then()
                .log().all()
                .and()
                .statusCode(200)
                .and()
                .rootPath(ENVELOPE + "deleteUserResponse")
                .and()
                .assertThat()
                .body("user.userID", equalTo(addedID))
                .body("user.login", equalTo(userToAdd.getLogin()))
                .body("user.type", equalTo(userToAdd.getType().toString()))
                .body("user.active", equalTo(Boolean.toString(userToAdd.isActive())));
    }

    @Test
    public void testActivateAndDeactivateUser() {
        RestAssured.baseURI = BASE_PATH;

        String userID = "cebbee82-2398-4dc2-a94d-a4c863286ff0";

        String deactivatePayload = pack("<gs:deactivateUserRequest>" +
                "<gs:userID>" + userID + "</gs:userID>" +
                "</gs:deactivateUserRequest>");

        sendRequest(deactivatePayload)
                .then()
                .log().all()
                .and()
                .statusCode(200)
                .and()
                .rootPath(ENVELOPE + "deactivateUserResponse")
                .body("user.userID", equalTo(userID))
                .body("user.active", equalTo("false"));

        String activatePayload = pack("<gs:activateUserRequest>" +
                "<gs:userID>" + userID + "</gs:userID>" +
                "</gs:activateUserRequest>");

        sendRequest(activatePayload)
                .then()
                .log().all()
                .and()
                .statusCode(200)
                .and()
                .rootPath(ENVELOPE + "activateUserResponse")
                .body("user.userID", equalTo(userID))
                .body("user.active", equalTo("true"));
    }
}
