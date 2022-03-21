package com.edu.tks.infrastructure.repository.user;

import com.edu.tks.exception.InputException;
import com.edu.tks.user.User;

public interface AddUser {
    void appendUser(User user) throws InputException;
}
