package com.edu.tks.ports.view.service.user;

import com.edu.tks.exception.NotFoundExceptionView;
import com.edu.tks.model.UserView;

import java.util.List;

public interface GetUsersUseCase {
    List<UserView> getAllUsers();
    UserView getUserByID(String userid) throws NotFoundExceptionView;
    UserView getUserByLogin(String login);
    List<UserView> getUsersByLogin(String login);
}
