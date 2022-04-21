package com.edu.tks.ports.soap.service.user;

import com.edu.tks.exception.BasicSOAPException;
import com.edu.tks.model.user.UserSOAP;

public interface SOAPRemoveUser {
    UserSOAP removeUser(String userid) throws BasicSOAPException;
}
