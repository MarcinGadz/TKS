package com.edu.tks.ports.soap.service.user;

import com.edu.tks.exception.SOAPNotFoundException;
import com.edu.tks.model.user.UserSOAP;

import java.util.List;

public interface SOAPGetUsers {
    List<UserSOAP> getAllUsers();
    UserSOAP getUserByID(String userid) throws SOAPNotFoundException;
    UserSOAP getUserByLogin(String login);
    List<UserSOAP> getUsersByLogin(String login);
}
