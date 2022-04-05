package com.edu.tks.user;

import com.edu.tks.exception.*;
import com.edu.tks.infrastructure.repository.user.AddUser;
import com.edu.tks.infrastructure.repository.user.ExtendRentals;
import com.edu.tks.infrastructure.repository.user.GetUsers;
import com.edu.tks.infrastructure.repository.user.RemoveUser;
import com.edu.tks.infrastructure.service.user.AddUserUseCase;
import com.edu.tks.infrastructure.service.user.ExtendRentalsUseCase;
import com.edu.tks.infrastructure.service.user.GetUsersUseCase;
import com.edu.tks.infrastructure.service.user.RemoveUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO check if UpdateUser should be done within getUser interface

@Service
public class UserService implements AddUserUseCase, GetUsersUseCase, RemoveUserUseCase, ExtendRentalsUseCase {

    private final AddUser addUser;
    private final ExtendRentals extendRentals;
    private final GetUsers getUsers;
    private final RemoveUser removeUser;

    @Autowired
    public UserService(AddUser addUser, ExtendRentals extendRentals, GetUsers getUsers, RemoveUser removeUser) {
        this.addUser = addUser;
        this.extendRentals = extendRentals;
        this.getUsers = getUsers;
        this.removeUser = removeUser;
    }

    @Override
    public synchronized User updateUserLogin(String userid, String newLogin) throws InputException {
        if (getUsers.getUserByLogin(newLogin) != null) {
            throw new InputException("This login already exists");
        }
        User user = addUser.updateUserLogin(userid, newLogin);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return getUsers.getAllUsers();
    }

    @Override
    public User getUserByID(String userid) throws NotFoundException {
        return getUsers.getUserByID(userid);
    }

    @Override
    public User getUserByLogin(String login) {
        return getUsers.getUserByLogin(login);
    }

    @Override
    public synchronized void extendRentReturnDays(String renterId, String userId, int days) throws RentalException, PermissionException, NotFoundException {
        extendRentals.extendRentReturnDays(renterId, userId, days);
    }

    @Override
    public List<User> getUsersByLogin(String login) {
        return getUsers.getUsersByLogin(login);
    }

    @Override
    public synchronized void appendUser(User user) throws InputException {
        addUser.appendUser(user);
    }

    @Override
    public User updateActive(String userId, boolean active) {
        return addUser.updateActive(userId, active);
    }

    @Override
    public synchronized void removeUser(String userid) throws BasicException {
        removeUser.removeUser(userid);
    }
}


