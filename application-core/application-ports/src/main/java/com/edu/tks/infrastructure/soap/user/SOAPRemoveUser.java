package com.edu.tks.infrastructure.soap.user;

import com.edu.tks.exception.BasicException;
import model.user.UserSOAPEntity;

public interface SOAPRemoveUser {
    UserSOAPEntity removeUser(String userid) throws BasicException;
}
