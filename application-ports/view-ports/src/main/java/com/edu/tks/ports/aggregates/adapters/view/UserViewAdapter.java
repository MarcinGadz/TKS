package com.edu.tks.ports.aggregates.adapters.view;

import com.edu.tks.exception.*;
import com.edu.tks.model.UserView;
import com.edu.tks.ports.aggregates.converters.view.UserViewConverter;
import com.edu.tks.ports.view.service.user.AddUserUseCase;
import com.edu.tks.ports.view.service.user.ExtendRentalsUseCase;
import com.edu.tks.ports.view.service.user.GetUsersUseCase;
import com.edu.tks.ports.view.service.user.RemoveUserUseCase;
import com.edu.tks.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserViewAdapter implements AddUserUseCase, GetUsersUseCase, RemoveUserUseCase, ExtendRentalsUseCase {

    @Autowired
    private UserService userService;

    @Override
    public void appendUser(UserView user) throws InputExceptionView {
        try {
            userService.appendUser(UserViewConverter.convertUserViewToUser(user));
        } catch (InputException e) {
            throw new InputExceptionView(e.getMessage());
        }
    }

    @Override
    public UserView updateUserLogin(String userid, String newLogin) throws InputExceptionView {
        try {
            return UserViewConverter.convertUserToUserView(userService.updateUserLogin(userid, newLogin));
        } catch (InputException e) {
            throw new InputExceptionView(e.getMessage());
        }
    }

    @Override
    public UserView updateActive(String userId, boolean active) {
        return UserViewConverter.convertUserToUserView(userService.updateActive(userId, active));
    }

    @Override
    public void extendRentReturnDays(String userId, int days) throws PermissionExceptionView, RentalExceptionView, NotFoundExceptionView {
        try {
            userService.extendRentReturnDays(userId, days);
        } catch (RentalException e) {
            throw new RentalExceptionView(e.getMessage());
        } catch (PermissionException e) {
            throw new PermissionExceptionView(e.getMessage());
        } catch (NotFoundException e) {
            throw new NotFoundExceptionView(e.getMessage());
        }
    }

    @Override
    public List<UserView> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(UserViewConverter::convertUserToUserView)
                .toList();
    }

    @Override
    public UserView getUserByID(String userid) throws NotFoundExceptionView {
        try {
            return UserViewConverter.convertUserToUserView(userService.getUserByID(userid));
        } catch (NotFoundException e) {
            throw new NotFoundExceptionView(e.getMessage());
        }
    }

    @Override
    public UserView getUserByLogin(String login) {
        return UserViewConverter.convertUserToUserView(userService.getUserByLogin(login));
    }

    @Override
    public List<UserView> getUsersByLogin(String login) {
        return userService.getUsersByLogin(login)
                .stream()
                .map(UserViewConverter::convertUserToUserView)
                .toList();
    }

    @Override
    public void removeUser(String userid) throws BasicExceptionView {
        try {
            userService.removeUser(userid);
        } catch (BasicException e) {
            throw new BasicExceptionView(e.getMessage());
        }
    }
}
