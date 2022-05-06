package com.edu.tks.ports.aggregates.adapters.soap;

import com.edu.tks.exception.*;
import com.edu.tks.model.user.UserSOAP;
import com.edu.tks.ports.aggregates.converters.soap.UserSOAPConverter;
import com.edu.tks.ports.soap.service.user.SOAPAddUser;
import com.edu.tks.ports.soap.service.user.SOAPGetUsers;
import com.edu.tks.ports.soap.service.user.SOAPRemoveUser;
import com.edu.tks.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserSOAPAdapter implements SOAPAddUser, SOAPGetUsers, SOAPRemoveUser {

    @Autowired
    private UserService userService;

    @Override
    public UserSOAP appendUser(UserSOAP user) throws SOAPInputException {
        try {
            userService.appendUser(UserSOAPConverter.convertUserSOAPToUser(user));
        } catch (InputException e) {
            throw new SOAPInputException(e.getMessage());
        }
         return user;
    }

    @Override
    public UserSOAP updateUserLogin(String userid, String newLogin) throws SOAPInputException {
        try {
            return UserSOAPConverter.convertUserToUserSOAP(userService.updateUserLogin(userid, newLogin));
        } catch (InputException e) {
            throw new SOAPInputException(e.getMessage());
        }
    }

    @Override
    public UserSOAP updateActive(String userId, boolean active) {
        return UserSOAPConverter.convertUserToUserSOAP(userService.updateActive(userId, active));
    }

    @Override
    public List<UserSOAP> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(UserSOAPConverter::convertUserToUserSOAP)
                .collect(Collectors.toList());
    }

    @Override
    public UserSOAP getUserByID(String userid) throws SOAPNotFoundException {
        try {
            return UserSOAPConverter.convertUserToUserSOAP(userService.getUserByID(userid));
        } catch (NotFoundException e) {
            throw new SOAPNotFoundException(e.getMessage());
        }
    }

    @Override
    public UserSOAP getUserByLogin(String login) {
        return UserSOAPConverter.convertUserToUserSOAP(userService.getUserByLogin(login));
    }

    @Override
    public List<UserSOAP> getUsersByLogin(String login) {
        return userService.getUsersByLogin(login).stream()
                .map(UserSOAPConverter::convertUserToUserSOAP)
                .collect(Collectors.toList());
    }

    @Override
    public UserSOAP removeUser(String userid) throws BasicSOAPException {
        try {
            return UserSOAPConverter.convertUserToUserSOAP(userService.removeUser(userid));
        } catch (BasicException e) {
            throw new BasicSOAPException(e.getMessage());
        }
    }
}