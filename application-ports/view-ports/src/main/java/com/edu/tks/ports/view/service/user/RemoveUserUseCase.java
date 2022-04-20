package com.edu.tks.ports.view.service.user;

import com.edu.tks.exception.BasicExceptionView;

public interface RemoveUserUseCase {
    void removeUser(String userid) throws BasicExceptionView;
}
