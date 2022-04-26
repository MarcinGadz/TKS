package com.edu.tks.clients;

import com.edu.tks.model.user.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class UserClient extends WebServiceGatewaySupport {

    private final String location;

    public UserClient(int port) {
        location = "http://localhost:" + port + "/ws";
    }

    public GetUsersResponse getUsers() {

        GetUsersRequest request = new GetUsersRequest();
        return (GetUsersResponse) getWebServiceTemplate().marshalSendAndReceive(
                location,
                request,
                new SoapActionCallback(
                "http://model.tks.edu.com/user/GetUsersRequest")
        );
    }

    public GetUserByIdResponse getUserByID(String userID) {

        GetUserByIDRequest request = new GetUserByIDRequest();
        request.setUserID(userID);

        return (GetUserByIdResponse) getWebServiceTemplate().marshalSendAndReceive(
                location,
                request,
                new SoapActionCallback(
                        "http://model.tks.edu.com/user/GetUserByIdRequest")
        );
    }

    public AddUserResponse addUser(UserSOAP user) {

        AddUserRequest request = new AddUserRequest();
        request.setUser(user);

        return (AddUserResponse) getWebServiceTemplate().marshalSendAndReceive(
                location,
                request,
                new SoapActionCallback(
                        "http://model.tks.edu.com/user/AddUserRequest")
        );
    }

    public DeleteUserResponse deleteUser(String userID) {

        DeleteUserRequest request = new DeleteUserRequest();
        request.setUserID(userID);

        return (DeleteUserResponse) getWebServiceTemplate().marshalSendAndReceive(
                location,
                request,
                new SoapActionCallback(
                        "http://model.tks.edu.com/user/DeleteUserRequest")
        );
    }

    public ActivateUserResponse activateUser(String userID) {

        ActivateUserRequest request = new ActivateUserRequest();
        request.setUserID(userID);

        return (ActivateUserResponse) getWebServiceTemplate().marshalSendAndReceive(
                location,
                request,
                new SoapActionCallback(
                        "http://model.tks.edu.com/user/ActivateUserRequest")
        );
    }

    public DeactivateUserResponse deactivateUser(String userID) {

        DeactivateUserRequest request = new DeactivateUserRequest();
        request.setUserID(userID);

        return (DeactivateUserResponse) getWebServiceTemplate().marshalSendAndReceive(
                location,
                request,
                new SoapActionCallback(
                        "http://model.tks.edu.com/user/DeactivateUserRequest")
        );
    }

}
