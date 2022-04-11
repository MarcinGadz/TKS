package com.edu.tks.ports.view.service.user;

import com.edu.tks.exception.InputException;
import com.edu.tks.model.UserView;

public interface AddUserUseCase {
    void appendUser(UserView user) throws InputException;
    UserView updateUserLogin(String userid, String newLogin) throws InputException;

    UserView updateActive(String userId, boolean active);
}
