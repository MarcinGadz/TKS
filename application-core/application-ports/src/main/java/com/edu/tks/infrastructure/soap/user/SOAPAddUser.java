package com.edu.tks.infrastructure.soap.user;

import com.edu.tks.exception.InputException;
import com.edu.tks.user.User;
import model.user.UserSOAPEntity;

public interface SOAPAddUser {
    UserSOAPEntity appendUser(UserSOAPEntity user) throws InputException;
    UserSOAPEntity updateUserLogin(String userid, String newLogin) throws InputException;
    UserSOAPEntity updateActive(String userId, boolean active);
}
