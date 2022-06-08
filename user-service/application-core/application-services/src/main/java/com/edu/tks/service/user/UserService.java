package com.edu.tks.service.user;

import com.edu.tks.exception.*;
import com.edu.tks.ports.infrastructure.repository.user.AddUser;
import com.edu.tks.ports.infrastructure.repository.user.GetUsers;
import com.edu.tks.ports.infrastructure.repository.user.RemoveUser;
import com.edu.tks.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

import static com.edu.tks.service.user.EventType.*;

@Service
public class UserService {

    private final AddUser addUser;
    private final GetUsers getUsers;
    private final RemoveUser removeUser;
    private final static String CHANNEL_OUT = "producer-out-0";

    private final static Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    private StreamBridge streamBridge;

    @Bean
    public Consumer<UserMessage> consumer() {
        return message -> {
            logger.info("Received: " + message);
            if (message.type().equals(CREATE)) {
                try {
                    confirmUserRegistrationInService(message.id());
                } catch (BasicException ignored) {

                }
            } else if(message.type().equals(CANCEL_CREATE)) {
                try {
                    removeUser(message.id());
                } catch (BasicException e) {
                    e.printStackTrace();
                }
            }
        };
    }

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
        user.setPending(true);
        addUser.appendUser(user);
        streamBridge.send(CHANNEL_OUT, new UserMessage(user.getUserID().toString(), CREATE));
    }

    private void confirmUserRegistrationInService(String userId) throws BasicException {
        var user = getUserByID(userId);
        user.setPending(false);
        update(user);
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

    private void update(User user) throws BasicException {
        removeUser.removeUser(user.getUserID().toString());
        addUser.appendUser(user);
    }
}


