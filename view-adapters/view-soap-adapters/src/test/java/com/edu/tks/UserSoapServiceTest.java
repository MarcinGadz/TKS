package com.edu.tks;

import com.edu.tks.model.RecordView;
import com.edu.tks.model.user.ObjectFactory;
import com.edu.tks.model.user.UserSOAP;
import com.edu.tks.model.user.UserTypeSOAP;
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
import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SoapServiceApplication.class)
//class UserWebserviceTest {
//
//    private final UserSOAP[] INIT_DATA = {
//            new ObjectFactory().createUserSOAP().setUserID("cebbee82-2398-4dc2-a94d-a4c863286ff0").firstName
//                    .("Eleanora").setActive(true).setUserType(UserTypeSOAP.CLIENT),
//            new ObjectFactory().createUserSOAP().setUserID("bbe979ba-ed9c-4028-8c18-97740c25ae99")
//                    .setName("Jason").setActive(, ).setUserType(lse, UserTypeSOAP.CLIENT),
//            new ObjectFactory().createUserSOAP().setUserID("c6f3fbf7-135b-498e-8154-3a5b9d291145")
//                    .setName("Chidi").setActive(true).setUserType(UserTypeSOAP.CLIENT),
//            new ObjectFactory().createUserSOAP().setUserID("f6abb370-5dbb-4473-bf8d-505bdcf6ccce")
//                    .setName("Tahani").setActive(true).setUserType(UserTypeSOAP.CLIENT),
//            new ObjectFactory().createUserSOAP().setUserID("73d2c12b-179e-4700-879d-8e3de56fe55f")
//                    .setName("Michael").setActive(true).setUserType(UserTypeSOAP.ADMINISTRATOR),
//            new ObjectFactory().createUserSOAP().setUserID("bfac0aaa-e7d2-4e33-9fa9-8111b14bcd58")
//                    .setName("DiscoJanet").setActive(true).setUserType(UserTypeSOAP.CLIENT)
//    };
//
//    private void checkRecordResponse(Response response, String recordPath, String recordID, String title, String artist, String releaseDate, boolean rented) {
//        assertEquals(recordID, response.getBody().xmlPath().getString("Envelope.Body." + recordPath + ".recordID"));
//        assertEquals(title, response.getBody().xmlPath().getString("Envelope.Body." + recordPath + ".title"));
//        assertEquals(artist, response.getBody().xmlPath().getString("Envelope.Body." + recordPath + ".artist"));
//        assertEquals(releaseDate, response.getBody().xmlPath().getString("Envelope.Body." + recordPath + ".releaseDate"));
//        assertEquals(rented ? "true" : "false", response.getBody().xmlPath().getString("Envelope.Body." + recordPath + ".rented"));
//    }
//
//    private void checkRecordResponse(Response response, String recordPath, String title, String artist, String releaseDate, boolean rented) {
//        assertEquals(title, response.getBody().xmlPath().getString("Envelope.Body." + recordPath + ".title"));
//        assertEquals(artist, response.getBody().xmlPath().getString("Envelope.Body." + recordPath + ".artist"));
//        assertEquals(releaseDate, response.getBody().xmlPath().getString("Envelope.Body." + recordPath + ".releaseDate"));
//        assertEquals(rented ? "true" : "false", response.getBody().xmlPath().getString("Envelope.Body." + recordPath + ".rented"));
//    }
//
//    private Response getUsers() {
//        try {
//            RestAssured.baseURI = BASE_PATH;
//            FileInputStream fileInputStream = new FileInputStream(new File("requests/GetUsers.xml"));
//
//            Response response = given()
//                    .header("content-type", "text/xml")
//                    .and()
//                    .body(IOUtils.toByteArray(fileInputStream))
//                    .when()
//                    .post("/ws")
//                    .then()
//                    .statusCode(200)
//                    .and()
//                    .log().all()
//                    .extract().response();
//
//            return response;
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private int getUsersLength() {
//        return getUsers().xmlPath().getNodeChildren("Envelope.Body.getUsersResponse.user").size();
//    }
//
//    private Response getUserById(String userID) {
//        RestAssured.baseURI = BASE_PATH;
//        String requestBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\""
//                + " xmlns:gs=\"http://model.tks.edu.com/user\">"
//                + "<soapenv:Header/>"
//                + "<soapenv:Body>"
//                + "<gs:getUserByIdRequest>"
//                + "<gs:id>" + userID + "</gs:id>"
//                + "</gs:getUserByIdRequest>"
//                + "</soapenv:Body>"
//                + "</soapenv:Envelope>";
//
//        Response response = given()
//                .header("content-type", "text/xml")
//                .and()
//                .body(requestBody.getBytes(StandardCharsets.UTF_8))
//                .when()
//                .post("/ws")
//                .then()
//                .statusCode(200)
//                .and()
//                .log().all()
//                .extract().response();
//
//        return response;
//    }
//
////    private Node getRecordByName(String name) {
////        Response response = getUsers();
////        return response.getBody().xmlPath().getNode("Envelope.Body.getRecordsResponse.record[title='" + name +"']");
////    }
//
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate rest;
//
//    private String BASE_PATH;
//
//    @PostConstruct
//    private void setBasePath() {
//        BASE_PATH = "http://localhost:" + port;
//    }
//
//    @Test
//    public void getUserById() {
//        Response response = getUserById("3e3719e5-8689-4e65-883f-4cd06cae7195");
//        checkRecordResponse(response, "getRecordByIdResponse.record", "3e3719e5-8689-4e65-883f-4cd06cae7195", "Solar Panic", "Thieves", "2021-10-13", false);
//    }
//
//    @Test
//    void getRecordsTests() {
//        Response response = getUsers();
//        checkRecordResponse(response, "getRecordsResponse.user[0]", "02cf35bf-d025-440b-a6ec-17cc6c77b021", "Moral Power", "Nothing but Lorde", "2020-03-04", false);
//        checkRecordResponse(response, "getRecordsResponse.user[1]", "3e3719e5-8689-4e65-883f-4cd06cae7195", "Solar Panic", "Thieves", "2021-10-13", false);
//    }
//
//    @Test
//    void addRecord() throws IOException {
//        RestAssured.baseURI = BASE_PATH;
//        FileInputStream fileInputStream = new FileInputStream(new File("requests/AddRecord.xml"));
//
//        int len = getUsersLength();
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
//        assertEquals(len + 1, getUsersLength());
//        checkRecordResponse(response, "addRecordResponse.record", "c1cf35bf-d025-440b-a6ec-17cc6c77b021", "SoapRecordTitle", "SoapRecordArtist", "2022-11-15", false);
//    }
//
//    private final UserView[] INIT_DATA = {
//            new UserView(UUID.fromString("cebbee82-2398-4dc2-a94d-a4c863286ff0"), "Eleanora", true, UserTypeView.CLIENT),
//            new UserView(UUID.fromString("bbe979ba-ed9c-4028-8c18-97740c25ae99"), "Jason", false, UserTypeView.CLIENT),
//            new UserView(UUID.fromString("c6f3fbf7-135b-498e-8154-3a5b9d291145"), "Chidi", true, UserTypeView.CLIENT),
//            new UserView(UUID.fromString("f6abb370-5dbb-4473-bf8d-505bdcf6ccce"), "Tahani", true, UserTypeView.CLIENT),
//            new UserView(UUID.fromString("73d2c12b-179e-4700-879d-8e3de56fe55f"), "Michael", true, UserTypeView.ADMINISTRATOR),
//            new UserView(UUID.fromString("bfac0aaa-e7d2-4e33-9fa9-8111b14bcd58"), "DiscoJanet", true, UserTypeView.CLIENT)
//    };
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate rest;
//
//    private String BASE_PATH;
//
//    @PostConstruct
//    private void setBasePath() {
//        BASE_PATH = "http://localhost:" + port + "/record-shop";
//    }
//
//    @Test
//    void getUsers() {
//        ParameterizedTypeReference<List<UserView>> typeRef = new ParameterizedTypeReference<>() {
//        };
//        ResponseEntity<List<UserView>> response = rest.exchange(BASE_PATH + "/users", HttpMethod.GET, null, typeRef);
//        assertTrue(response.getStatusCode().is2xxSuccessful());
//        List<UserView> body = response.getBody();
//        assertNotNull(body);
//        assertEquals(INIT_DATA.length, body.size());
//        for (int i = 0; i < INIT_DATA.length; i++) {
//            assertEquals(INIT_DATA[i], body.get(i));
//        }
//    }
//
//    @Test
//    void getUserById() {
//        ResponseEntity<UserView> response = rest.getForEntity(BASE_PATH + "/users/" + INIT_DATA[0].getUserID().toString(), UserView.class);
//        assertTrue(response.getStatusCode().is2xxSuccessful());
//        assertEquals(INIT_DATA[0], response.getBody());
//    }
//
//    @Test
//    void getNonExistingUserById() {
//        ResponseEntity<UserView> response = rest.getForEntity(BASE_PATH + "/users/" + UUID.randomUUID(), UserView.class);
//        assertEquals(404, response.getStatusCode().value());
//        assertNull(response.getBody().getUserID());
//    }
//
//    @Test
//    void addDeleteUser() {
//        String login = "login1234";
//        UserTypeView userType = UserTypeView.CLIENT;
//
//        UserView newUser = new UserView(login, userType);
//        ResponseEntity<UserView> response = rest.postForEntity(BASE_PATH + "/users", newUser, UserView.class);
//        assertTrue(response.getStatusCode().is2xxSuccessful());
//
//        UserView body = response.getBody();
//        assertNotNull(body);
//        assertEquals(login, body.getLogin());
//        assertEquals(userType, body.getType());
//
//        response = rest.exchange(BASE_PATH + "/users/" + body.getUserID().toString(), HttpMethod.DELETE, null, UserView.class);
//        assertTrue(response.getStatusCode().is2xxSuccessful());
//
//        ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<>() {
//        };
//        ResponseEntity<List<User>> all = rest.exchange(BASE_PATH + "/users", HttpMethod.GET, null, typeRef);
//        assertTrue(all.getStatusCode().is2xxSuccessful());
//        assertEquals(INIT_DATA.length, all.getBody().size());
//    }
//
//    @Test
//    void changeUserLogin() {
//        var newLogin = "newfancylogin";
//        var oldLogin = INIT_DATA[0].getLogin();
//        UserView updated = new UserView();
//        updated.setLogin(newLogin);
//
//        HttpEntity<UserView> req = new HttpEntity<UserView>(updated);
//        ResponseEntity<UserView> response = rest.exchange(
//                BASE_PATH + "/users/" + INIT_DATA[0].getUserID() + "/changeLogin",
//                HttpMethod.PUT,
//                req,
//                UserView.class
//        );
//        assertTrue(response.getStatusCode().is2xxSuccessful());
//
//        User responseUpdated = rest.getForObject(BASE_PATH + "/users/" + INIT_DATA[0].getUserID().toString(), User.class);
//        assertEquals(newLogin, responseUpdated.getLogin());
//
//        req = new HttpEntity<UserView>(INIT_DATA[0]);
//        ResponseEntity<UserView> updateOldLogin = rest.exchange(
//                BASE_PATH + "/users/" + INIT_DATA[0].getUserID() + "/changeLogin",
//                HttpMethod.PUT,
//                req,
//                UserView.class
//        );
//        assertTrue(updateOldLogin.getStatusCode().is2xxSuccessful());
//        responseUpdated = rest.getForObject(BASE_PATH + "/users/" + INIT_DATA[0].getUserID().toString(), User.class);
//        assertEquals(oldLogin, responseUpdated.getLogin());
//
//    }
//
//    @Test
//    void activateDeactivateUser() {
//        assertFalse(INIT_DATA[1].isActive());
//        ResponseEntity<Object> response = rest.exchange(BASE_PATH + "/users/" + INIT_DATA[1].getUserID().toString() + "/activate",
//                HttpMethod.PUT,
//                null,
//                Object.class
//        );
//        assertTrue(response.getStatusCode().is2xxSuccessful());
//
//        ResponseEntity<User> responseUser = rest.getForEntity(BASE_PATH + "/users/" + INIT_DATA[1].getUserID().toString(), User.class);
//        assertTrue(responseUser.getStatusCode().is2xxSuccessful());
//        assertTrue(responseUser.getBody().isActive());
//
//        ResponseEntity<Object> responseDeactivate = rest.exchange(BASE_PATH + "/users/" + responseUser.getBody().getUserID().toString() + "/deactivate",
//                HttpMethod.PUT,
//                null,
//                Object.class
//        );
//        assertTrue(response.getStatusCode().is2xxSuccessful());
//
//        responseUser = rest.getForEntity(BASE_PATH + "/users/" + responseUser.getBody().getUserID().toString(), User.class);
//        assertTrue(responseUser.getStatusCode().is2xxSuccessful());
//        assertFalse(responseUser.getBody().isActive());
//    }
//
//}