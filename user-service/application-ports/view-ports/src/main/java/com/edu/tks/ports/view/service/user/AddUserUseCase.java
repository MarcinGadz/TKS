package com.edu.tks.ports.view.service.user;

import com.edu.tks.rest.exception.InputExceptionView;
import com.edu.tks.rest.model.UserView;

public interface AddUserUseCase {
    void appendUser(UserView user) throws InputExceptionView;
    UserView updateUserLogin(String userid, String newLogin) throws InputExceptionView;

    UserView updateActive(String userId, boolean active);
}
