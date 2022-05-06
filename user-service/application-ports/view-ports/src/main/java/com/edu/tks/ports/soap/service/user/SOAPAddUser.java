package com.edu.tks.ports.soap.service.user;

import com.edu.tks.exception.SOAPInputException;
import com.edu.tks.model.user.UserSOAP;

public interface SOAPAddUser {
    UserSOAP appendUser(UserSOAP user) throws SOAPInputException;
    UserSOAP updateUserLogin(String userid, String newLogin) throws SOAPInputException;
    UserSOAP updateActive(String userId, boolean active);
}
