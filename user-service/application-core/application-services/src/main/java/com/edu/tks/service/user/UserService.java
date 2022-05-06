package com.edu.tks.service.user;

import com.edu.tks.exception.*;
import com.edu.tks.ports.infrastructure.repository.user.AddUser;
import com.edu.tks.ports.infrastructure.repository.user.GetUsers;
import com.edu.tks.ports.infrastructure.repository.user.RemoveUser;
import com.edu.tks.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final AddUser addUser;
    private final GetUsers getUsers;
    private final RemoveUser removeUser;

    @Autowired
    public UserService(AddUser addUser, GetUsers getUsers, RemoveUser removeUser) {
        this.addUser = addUser;
        this.getUsers = getUsers;
        this.removeUser = removeUser;
    }

    
    public synchronized User updateUserLogin(String userid, String newLogin) throws InputException {
        if (getUsers.getUserByLogin(newLogin) != null) {
            throw new InputException("This login already exists");
        }
        User user = addUser.updateUserLogin(userid, newLogin);
        return user;
    }

    
    public List<User> getAllUsers() {
        return getUsers.getAllUsers();
    }

    
    public User getUserByID(String userid) throws NotFoundException {
        return getUsers.getUserByID(userid);
    }

    
    public User getUserByLogin(String login) {
        return getUsers.getUserByLogin(login);
    }

    
    public List<User> getUsersByLogin(String login) {
        return getUsers.getUsersByLogin(login);
    }

    
    public synchronized void appendUser(User user) throws InputException {
        addUser.appendUser(user);
    }

    
    public User updateActive(String userId, boolean active) {
        return addUser.updateActive(userId, active);
    }

    
    public synchronized User removeUser(String userid) throws BasicException {
        return removeUser.removeUser(userid);
    }
}


