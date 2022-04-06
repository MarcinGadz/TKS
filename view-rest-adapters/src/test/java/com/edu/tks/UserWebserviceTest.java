package com.edu.tks;

import com.edu.tks.user.User;
import com.edu.tks.user.UserType;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserWebserviceTest {

    private final User[] INIT_DATA = {
            new User(UUID.fromString("cebbee82-2398-4dc2-a94d-a4c863286ff0"), "Eleanora", true, UserType.CLIENT),
            new User(UUID.fromString("bbe979ba-ed9c-4028-8c18-97740c25ae99"), "Jason", false, UserType.CLIENT),
            new User(UUID.fromString("c6f3fbf7-135b-498e-8154-3a5b9d291145"), "Chidi", true, UserType.CLIENT),
            new User(UUID.fromString("f6abb370-5dbb-4473-bf8d-505bdcf6ccce"), "Tahani", true, UserType.CLIENT),
            new User(UUID.fromString("73d2c12b-179e-4700-879d-8e3de56fe55f"), "Michael", true, UserType.ADMINISTRATOR),
            new User(UUID.fromString("bfac0aaa-e7d2-4e33-9fa9-8111b14bcd58"), "DiscoJanet", true, UserType.RENTER)
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
        ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<List<User>> response = rest.exchange(BASE_PATH + "/users", HttpMethod.GET, null, typeRef);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        List<User> body = response.getBody();
        assertNotNull(body);
        assertEquals(INIT_DATA.length, body.size());
        for (int i = 0; i < INIT_DATA.length; i++) {
            assertEquals(INIT_DATA[i], body.get(i));
        }
    }

    @Test
    void getUserById() {
        ResponseEntity<User> response = rest.getForEntity(BASE_PATH + "/users/" + INIT_DATA[0].getUserID().toString(), User.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(INIT_DATA[0], response.getBody());
    }

    @Test
    void getNonExistingUserById() {
        ResponseEntity<User> response = rest.getForEntity(BASE_PATH + "/users/" + UUID.randomUUID(), User.class);
        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody().getUserID());
    }

    @Test
    void addDeleteUser() {
        String login = "login1234";
        UserType userType = UserType.CLIENT;

        User newUser = new User(login, userType);
        ResponseEntity<User> response = rest.postForEntity(BASE_PATH + "/users", newUser, User.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        User body = response.getBody();
        assertNotNull(body);
        assertEquals(login, body.getLogin());
        assertEquals(userType, body.getType());

        response = rest.exchange(BASE_PATH + "/users/" + body.getUserID().toString(), HttpMethod.DELETE, null, User.class);
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
        User updated = new User();
        updated.setLogin(newLogin);

        HttpEntity<User> req = new HttpEntity<>(updated);
        ResponseEntity<User> response = rest.exchange(
                BASE_PATH + "/users/" + INIT_DATA[0].getUserID() + "/changeLogin",
                HttpMethod.PUT,
                req,
                User.class
        );
        assertTrue(response.getStatusCode().is2xxSuccessful());

        User responseUpdated = rest.getForObject(BASE_PATH + "/users/" + INIT_DATA[0].getUserID().toString(), User.class);
        assertEquals(newLogin, responseUpdated.getLogin());

        req = new HttpEntity<>(INIT_DATA[0]);
        ResponseEntity<User> updateOldLogin = rest.exchange(
                BASE_PATH + "/users/" + INIT_DATA[0].getUserID() + "/changeLogin",
                HttpMethod.PUT,
                req,
                User.class
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