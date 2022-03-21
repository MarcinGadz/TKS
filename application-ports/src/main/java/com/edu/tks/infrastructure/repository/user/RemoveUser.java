package com.edu.tks.infrastructure.repository.user;

import com.edu.tks.exception.BasicException;

public interface RemoveUser {
    void removeUser(String userid) throws BasicException;
}
