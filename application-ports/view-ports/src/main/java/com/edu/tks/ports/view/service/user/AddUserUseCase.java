package com.edu.tks.ports.view.service.user;

import com.edu.tks.exception.SOAPInputException;
import com.edu.tks.model.UserView;

public interface AddUserUseCase {
    void appendUser(UserView user) throws SOAPInputException;
    UserView updateUserLogin(String userid, String newLogin) throws SOAPInputException;

    UserView updateActive(String userId, boolean active);
}
