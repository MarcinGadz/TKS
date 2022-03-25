package com.edu.tks.user;

import com.edu.tks.exception.*;
import com.edu.tks.infrastructure.repository.user.AddUser;
import com.edu.tks.infrastructure.repository.user.ExtendRentals;
import com.edu.tks.infrastructure.repository.user.GetUsers;
import com.edu.tks.infrastructure.repository.user.RemoveUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO check if UpdateUser should be done within getUser interface

@Service
public class UserService {

    private final AddUser addUserUseCase;
    private final ExtendRentals extendRentalsUseCase;
    private final GetUsers getUsersUseCase;
    private final RemoveUser removeUserUseCase;

    @Autowired
    public UserService(AddUser addUserUseCase, ExtendRentals extendRentalsUseCase, GetUsers getUsersUseCase, RemoveUser removeUserUseCase) {
        this.addUserUseCase = addUserUseCase;
        this.extendRentalsUseCase = extendRentalsUseCase;
        this.getUsersUseCase = getUsersUseCase;
        this.removeUserUseCase = removeUserUseCase;
    }

    public synchronized User setUserLogin(String userid, String newLogin) throws InputException, NotFoundException {
        if (getUsersUseCase.getUserByLogin(newLogin) != null) {
            throw new InputException("This login already exists");
        }
        User user = addUserUseCase.updateUserLogin(userid, newLogin);
        return user;
    }

    public List<User> getAllUsers() {
        return getUsersUseCase.getAllUsers();
    }

    public User getUserByID(String userid) throws NotFoundException {
        return getUsersUseCase.getUserByID(userid);
    }

    public User getUserByLogin(String login) {
        return getUsersUseCase.getUserByLogin(login);
    }

    public synchronized void extendRentReturnDays(String renterId, String userId, int days) throws RentalException, PermissionException, NotFoundException {
        extendRentalsUseCase.extendRentReturnDays(renterId, userId, days);
    }


    public List<User> getUsersByLogin(String login) {
        return getUsersUseCase.getUsersByLogin(login);
    }

    public synchronized void appendUser(User user) throws InputException {
        addUserUseCase.appendUser(user);
    }

    public synchronized void removeUser(String userid) throws BasicException {
        removeUserUseCase.removeUser(userid);
    }

    public User activate(String userID) {
        User u = addUserUseCase.updateActive(userID, true);
        return u;
    }

    public User deactivate(String userID) {
        User u = addUserUseCase.updateActive(userID,false);
        return u;
    }
}


