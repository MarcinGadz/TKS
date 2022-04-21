package com.edu.tks;

import com.edu.tks.model.UserView;
import com.edu.tks.model.UserTypeView;
import com.edu.tks.soap.RestServiceApplication;
import com.edu.tks.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RestServiceApplication.class)
class UserWebserviceTest {

    private final UserView[] INIT_DATA = {
            new UserView(UUID.fromString("cebbee82-2398-4dc2-a94d-a4c863286ff0"), "Eleanora", true, UserTypeView.CLIENT),
            new UserView(UUID.fromString("bbe979ba-ed9c-4028-8c18-97740c25ae99"), "Jason", false, UserTypeView.CLIENT),
            new UserView(UUID.fromString("c6f3fbf7-135b-498e-8154-3a5b9d291145"), "Chidi", true, UserTypeView.CLIENT),
            new UserView(UUID.fromString("f6abb370-5dbb-4473-bf8d-505bdcf6ccce"), "Tahani", true, UserTypeView.CLIENT),
            new UserView(UUID.fromString("73d2c12b-179e-4700-879d-8e3de56fe55f"), "Michael", true, UserTypeView.ADMINISTRATOR),
            new UserView(UUID.fromString("bfac0aaa-e7d2-4e33-9fa9-8111b14bcd58"), "DiscoJanet", true, UserTypeView.CLIENT)
    };

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    private String BASE_PATH;

    @PostConstruct
    private void setBasePath() {
        BASE_PATH = "http://localhost:" + port + "/record-shop";
    }

    @Test
    void getUsers() {
        ParameterizedTypeReference<List<UserView>> typeRef = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<List<UserView>> response = rest.exchange(BASE_PATH + "/users", HttpMethod.GET, null, typeRef);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        List<UserView> body = response.getBody();
        assertNotNull(body);
        assertEquals(INIT_DATA.length, body.size());
        for (int i = 0; i < INIT_DATA.length; i++) {
            assertEquals(INIT_DATA[i], body.get(i));
        }
    }

    @Test
    void getUserById() {
        ResponseEntity<UserView> response = rest.getForEntity(BASE_PATH + "/users/" + INIT_DATA[0].getUserID().toString(), UserView.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(INIT_DATA[0], response.getBody());
    }

    @Test
    void getNonExistingUserById() {
        ResponseEntity<UserView> response = rest.getForEntity(BASE_PATH + "/users/" + UUID.randomUUID(), UserView.class);
        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody().getUserID());
    }

    @Test
    void addDeleteUser() {
        String login = "login1234";
        UserTypeView userType = UserTypeView.CLIENT;

        UserView newUser = new UserView(login, userType);
        ResponseEntity<UserView> response = rest.postForEntity(BASE_PATH + "/users", newUser, UserView.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        UserView body = response.getBody();
        assertNotNull(body);
        assertEquals(login, body.getLogin());
        assertEquals(userType, body.getType());

        response = rest.exchange(BASE_PATH + "/users/" + body.getUserID().toString(), HttpMethod.DELETE, null, UserView.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<List<User>> all = rest.exchange(BASE_PATH + "/users", HttpMethod.GET, null, typeRef);
        assertTrue(all.getStatusCode().is2xxSuccessful());
        assertEquals(INIT_DATA.length, all.getBody().size());
    }

    @Test
    void changeUserLogin() {
        var newLogin = "newfancylogin";
        var oldLogin = INIT_DATA[0].getLogin();
        UserView updated = new UserView();
        updated.setLogin(newLogin);

        HttpEntity<UserView> req = new HttpEntity<UserView>(updated);
        ResponseEntity<UserView> response = rest.exchange(
                BASE_PATH + "/users/" + INIT_DATA[0].getUserID() + "/changeLogin",
                HttpMethod.PUT,
                req,
                UserView.class
        );
        assertTrue(response.getStatusCode().is2xxSuccessful());

        User responseUpdated = rest.getForObject(BASE_PATH + "/users/" + INIT_DATA[0].getUserID().toString(), User.class);
        assertEquals(newLogin, responseUpdated.getLogin());

        req = new HttpEntity<UserView>(INIT_DATA[0]);
        ResponseEntity<UserView> updateOldLogin = rest.exchange(
                BASE_PATH + "/users/" + INIT_DATA[0].getUserID() + "/changeLogin",
                HttpMethod.PUT,
                req,
                UserView.class
        );
        assertTrue(updateOldLogin.getStatusCode().is2xxSuccessful());
        responseUpdated = rest.getForObject(BASE_PATH + "/users/" + INIT_DATA[0].getUserID().toString(), User.class);
        assertEquals(oldLogin, responseUpdated.getLogin());

    }

    @Test
    void activateDeactivateUser() {
        assertFalse(INIT_DATA[1].isActive());
        ResponseEntity<Object> response = rest.exchange(BASE_PATH + "/users/" + INIT_DATA[1].getUserID().toString() + "/activate",
                HttpMethod.PUT,
                null,
                Object.class
        );
        assertTrue(response.getStatusCode().is2xxSuccessful());

        ResponseEntity<User> responseUser = rest.getForEntity(BASE_PATH + "/users/" + INIT_DATA[1].getUserID().toString(), User.class);
        assertTrue(responseUser.getStatusCode().is2xxSuccessful());
        assertTrue(responseUser.getBody().isActive());

        ResponseEntity<Object> responseDeactivate = rest.exchange(BASE_PATH + "/users/" + responseUser.getBody().getUserID().toString() + "/deactivate",
                HttpMethod.PUT,
                null,
                Object.class
        );
        assertTrue(response.getStatusCode().is2xxSuccessful());

        responseUser = rest.getForEntity(BASE_PATH + "/users/" + responseUser.getBody().getUserID().toString(), User.class);
        assertTrue(responseUser.getStatusCode().is2xxSuccessful());
        assertFalse(responseUser.getBody().isActive());
    }

}