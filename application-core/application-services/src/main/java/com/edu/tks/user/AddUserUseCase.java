package com.edu.tks.user;

import com.edu.tks.exception.InputException;
import com.edu.tks.user.User;

public interface AddUserUseCase {
    void appendUser(User user) throws InputException;
    User updateUserLogin(String userid, String newLogin) throws InputException;

    User updateActive(String userId, boolean active);
}
