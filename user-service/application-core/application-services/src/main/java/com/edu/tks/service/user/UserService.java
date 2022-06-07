package com.edu.tks.service.user;

import com.edu.tks.exception.*;
import com.edu.tks.ports.infrastructure.repository.user.AddUser;
import com.edu.tks.ports.infrastructure.repository.user.GetUsers;
import com.edu.tks.ports.infrastructure.repository.user.RemoveUser;
import com.edu.tks.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.edu.tks.service.user.EventType.*;

@Service
public class UserService {

    private final AddUser addUser;
    private final GetUsers getUsers;
    private final RemoveUser removeUser;
    private final static String CHANNEL_OUT = "producer-out-0";

    @Autowired
    private StreamBridge streamBridge;

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
        user.deactivate();
        addUser.appendUser(user);
        streamBridge.send(CHANNEL_OUT, new UserMessage(user.getUserID().toString(), CREATE));
    }

    
    public User updateActive(String userId, boolean active) {
        var user = addUser.updateActive(userId, active);
        streamBridge.send(CHANNEL_OUT, new UserMessage(userId, active ? ACTIVATE : DEACTIVATE));
        return user;
    }

    
    public synchronized User removeUser(String userid) throws BasicException {
        var user = removeUser.removeUser(userid);
        streamBridge.send(CHANNEL_OUT, new UserMessage(userid, REMOVE));
        return user;
    }
}


