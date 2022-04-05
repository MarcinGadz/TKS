package com.edu.tks.infrastructure.service.user;

import com.edu.tks.exception.BasicException;

public interface RemoveUserUseCase {
    void removeUser(String userid) throws BasicException;
}
