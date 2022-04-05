package com.edu.tks.user;

import com.edu.tks.exception.BasicException;

public interface RemoveUserUseCase {
    void removeUser(String userid) throws BasicException;
}
