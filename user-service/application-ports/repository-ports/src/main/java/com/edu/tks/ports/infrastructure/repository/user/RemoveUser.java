package com.edu.tks.ports.infrastructure.repository.user;

import com.edu.tks.exception.BasicException;
import com.edu.tks.user.User;

public interface RemoveUser {
    User removeUser(String userid) throws BasicException;
}
