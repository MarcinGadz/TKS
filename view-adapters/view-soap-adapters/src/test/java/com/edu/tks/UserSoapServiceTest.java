package com.edu.tks;

import com.edu.tks.clients.UserClient;
import com.edu.tks.model.user.UserSOAP;
import com.edu.tks.model.user.UserTypeSOAP;
import com.edu.tks.soap.SoapServiceApplication;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SoapServiceApplication.class)
class UserSoapServiceTest {

    private List<UserSOAP> testData;

    @LocalServerPort
    private int port;

    private UserClient userClient;


    @PostConstruct
    private void init() {
        String BASE_PATH = "http://localhost:" + port;

        Jaxb2Marshaller marshaller = TestUtils.getUserMarshaller();
        userClient = new UserClient(port);
        userClient.setDefaultUri(BASE_PATH + "/ws");
        userClient.setMarshaller(marshaller);
        userClient.setUnmarshaller(marshaller);

        testData = new ArrayList<>();
        UserSOAP user = new UserSOAP();
        user.setUserID("cebbee82-2398-4dc2-a94d-a4c863286ff0");
        user.setLogin("Eleanora");
        user.setActive(true);
        user.setType(UserTypeSOAP.CLIENT);
        testData.add(user);
        user = new UserSOAP();
        user.setUserID("bbe979ba-ed9c-4028-8c18-97740c25ae99");
        user.setLogin("Jason");
        user.setActive(false);
        user.setType(UserTypeSOAP.CLIENT);
        testData.add(user);
        user = new UserSOAP();
        user.setUserID("c6f3fbf7-135b-498e-8154-3a5b9d291145");
        user.setLogin("Chidi");
        user.setActive(true);
        user.setType(UserTypeSOAP.CLIENT);
        testData.add(user);
        user = new UserSOAP();
        user.setUserID("f6abb370-5dbb-4473-bf8d-505bdcf6ccce");
        user.setLogin("Tahani");
        user.setActive(true);
        user.setType(UserTypeSOAP.CLIENT);
        testData.add(user);
        user = new UserSOAP();
        user.setUserID("73d2c12b-179e-4700-879d-8e3de56fe55f");
        user.setLogin("Michael");
        user.setActive(true);
        user.setType(UserTypeSOAP.ADMINISTRATOR);
        testData.add(user);
        user = new UserSOAP();
        user.setUserID("bfac0aaa-e7d2-4e33-9fa9-8111b14bcd58");
        user.setLogin("DiscoJanet");
        user.setActive(true);
        user.setType(UserTypeSOAP.CLIENT);
        testData.add(user);
    }

    @Test
    public void testGetUserById() {
        UserSOAP user = userClient.getUserByID("cebbee82-2398-4dc2-a94d-a4c863286ff0").getUser();
        assertEquals(user.getUserID(), "cebbee82-2398-4dc2-a94d-a4c863286ff0");
    }

    @Test
    public void testGetUsers() {
        List<UserSOAP> users = userClient.getUsers().getUser();
        assertEquals(users.size(), testData.size());
        for (int i = 0; i < testData.size(); i++) {
            assertEquals(users.get(i).getUserID(), testData.get(i).getUserID());
            assertEquals(users.get(i).getLogin(), testData.get(i).getLogin());
            assertEquals(users.get(i).getType(), testData.get(i).getType());
            assertEquals(users.get(i).isActive(), testData.get(i).isActive());
        }
    }

    @Test
    public void testAddAndRemoveUser() {
        UserSOAP newUser = new UserSOAP();
        newUser.setUserID("93191668-0d08-42a5-8dc3-dae8a3a13f1b");
        newUser.setLogin("SOAPTestUser0");
        newUser.setType(UserTypeSOAP.CLIENT);
        newUser.setActive(true);

        UserSOAP addResponseUser = userClient.addUser(newUser).getUser();
        assertEquals(addResponseUser.getLogin(), newUser.getLogin());
        assertEquals(addResponseUser.isActive(), newUser.isActive());
        assertEquals(addResponseUser.getType(), newUser.getType());

        UserSOAP deleteResponseUser = userClient.deleteUser(addResponseUser.getUserID()).getUser();
        assertEquals(deleteResponseUser.getUserID(), addResponseUser.getUserID());
        assertEquals(deleteResponseUser.getLogin(), addResponseUser.getLogin());
        assertEquals(deleteResponseUser.isActive(), addResponseUser.isActive());
        assertEquals(deleteResponseUser.getType(), addResponseUser.getType());
    }

    @Test
    public void testDeactivateAndActivateUser() {
        String userID = "cebbee82-2398-4dc2-a94d-a4c863286ff0";

        UserSOAP deactivatedUser = userClient.deactivateUser(userID).getUser();
        assertEquals(deactivatedUser.getUserID(), userID);
        assertFalse(deactivatedUser.isActive());

        UserSOAP activatedUser = userClient.activateUser(userID).getUser();
        assertEquals(activatedUser.getUserID(), userID);
        assertTrue(activatedUser.isActive());
    }
}
