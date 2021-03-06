package com.edu.tks.ports.infrastructure.repository.user;

import com.edu.tks.exception.InputException;
import com.edu.tks.user.User;

public interface AddUser {
    void appendUser(User user) throws InputException;
    User updateUserLogin(String userid, String newLogin);

    User updateActive(String userId, boolean active);
}
