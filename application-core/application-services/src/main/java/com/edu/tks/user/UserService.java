package com.edu.tks.user;

import com.edu.tks.exception.*;
import com.edu.tks.infrastructure.repository.user.AddUser;
import com.edu.tks.infrastructure.repository.user.ExtendRentals;
import com.edu.tks.infrastructure.repository.user.GetUsers;
import com.edu.tks.infrastructure.repository.user.RemoveUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService<Repository extends AddUser & ExtendRentals & GetUsers & RemoveUser> {
    Repository repository;

    public synchronized void setUserLogin(String userid, String newLogin) throws InputException, NotFoundException {
        User user = repository.getUserByID(userid);

        if (user.getLogin().equals(newLogin)) {
            throw new InputException("This login is already set");
        }

        if (repository.getUserByLogin(newLogin) != null) {
            throw new InputException("This login already exists");
        }

        user.setLogin(newLogin);
    }

    public List<User> getAllUsers() {
        return repository.getAllUsers();
    }

    public User getUserByID(String userid) throws NotFoundException {
        return repository.getUserByID(userid);
    }

    public User getUserByLogin(String login) {
        return repository.getUserByLogin(login);
    }

    public synchronized void extendRentReturnDays(String renterId, String userId, int days) throws RentalException, PermissionException, NotFoundException {
        repository.extendRentReturnDays(renterId, userId, days);
    }


    public List<User> getUsersByLogin(String login) {
        return repository.getUsersByLogin(login);
    }

    public synchronized void appendUser(User user) throws InputException {
        repository.appendUser(user);
    }

    public synchronized void removeUser(String userid) throws BasicException {
        repository.removeUser(userid);
    }

}


