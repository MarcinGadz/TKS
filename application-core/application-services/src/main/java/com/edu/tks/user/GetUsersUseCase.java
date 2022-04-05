package com.edu.tks.user;

import com.edu.tks.exception.NotFoundException;
import com.edu.tks.user.User;

import java.util.List;

public interface GetUsersUseCase {
    List<User> getAllUsers();
    User getUserByID(String userid) throws NotFoundException;
    User getUserByLogin(String login);
    List<User> getUsersByLogin(String login);
}
