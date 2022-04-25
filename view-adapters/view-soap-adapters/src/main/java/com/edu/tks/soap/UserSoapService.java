package com.edu.tks.soap;

import com.edu.tks.exception.*;
import com.edu.tks.model.user.*;
import com.edu.tks.ports.soap.service.user.SOAPAddUser;
import com.edu.tks.ports.soap.service.user.SOAPGetUsers;
import com.edu.tks.ports.soap.service.user.SOAPRemoveUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.UUID;

@Endpoint
public class UserSoapService {

    private static final String NAMESPACE_URI = "http://model.tks.edu.com/user";

    @Autowired
    private SOAPAddUser soapAddUser;
    @Autowired
    private SOAPGetUsers soapGetUsers;
    @Autowired
    private SOAPRemoveUser soapRemoveUser;

    public UserSoapService() {}

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUsersRequest")
    @ResponsePayload
    public GetUsersResponse getUsers() {
        GetUsersResponse response = new GetUsersResponse();
        for (UserSOAP user : soapGetUsers.getAllUsers()) {
            response.getUser().add(user);
        }
        return response;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserByIDRequest")
    @ResponsePayload
    public GetUserByIdResponse getUserById(@RequestPayload GetUserByIDRequest request) throws SOAPNotFoundException {
        GetUserByIdResponse response = new GetUserByIdResponse();
        response.setUser(soapGetUsers.getUserByID(request.getUserID()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addUserRequest")
    @ResponsePayload
    public AddUserResponse addUser(@RequestPayload AddUserRequest request) throws SOAPInputException {
        try {
            String login = request.getUser().getLogin();

            if (!login.matches("^[a-zA-Z0-9_-]{8,16}$")) {
                throw new SOAPInputException("Login must be between 8 and 16 characters");
            }

            UserTypeSOAP type = request.getUser().getType();

            UserSOAP user = new UserSOAP();
            user.setUserID(UUID.randomUUID().toString());
            user.setLogin(request.getUser().getLogin());
            user.setType(request.getUser().getType());
            user.setActive(request.getUser().isActive());

            soapAddUser.appendUser(user);

            AddUserResponse response = new AddUserResponse();
            response.setUser(user);
            return response;
        } catch (NullPointerException
                 | IllegalArgumentException
                 | IllegalStateException e) {
            throw new SOAPInputException("Login Invalid");
        }
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
    @ResponsePayload
    public DeleteUserResponse deleteUser(@RequestPayload DeleteUserRequest request) throws BasicSOAPException, SOAPNotFoundException {
        UserSOAP user = soapGetUsers.getUserByID(request.getUserID());
        soapRemoveUser.removeUser(request.getUserID());
        DeleteUserResponse response = new DeleteUserResponse();
        response.setUser(user);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "changeUserLoginRequest")
    @ResponsePayload
    public ChangeUserLoginResponse changeUserLogin(@RequestPayload ChangeUserLoginRequest request) throws SOAPInputException,
            SOAPNotFoundException {
        String login = request.getLogin();
        if (!login.matches("^[a-zA-Z0-9_-]{8,16}$")) {
            throw new SOAPInputException("Login must be between 8 and 16 characters");
        }
        UserSOAP user = soapAddUser.updateUserLogin(request.getUserID(), login);
        ChangeUserLoginResponse response = new ChangeUserLoginResponse();
        response.setUser(user);
        return response;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "activateUserRequest")
    @ResponsePayload
    public ActivateUserResponse activateUser(@RequestPayload ActivateUserRequest request) throws SOAPNotFoundException, SOAPInputException {
        ActivateUserResponse response = new ActivateUserResponse();
        response.setUser(soapAddUser.updateActive(request.getUserID(), true));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deactivateUserRequest")
    @ResponsePayload
    public DeactivateUserResponse deactivateUser(@RequestPayload DeactivateUserRequest request) throws SOAPNotFoundException, SOAPInputException {
        DeactivateUserResponse response = new DeactivateUserResponse();
        response.setUser(soapAddUser.updateActive(request.getUserID(), false));
        return response;
    }
}
