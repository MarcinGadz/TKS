package com.edu.tks.infrastructure.soap.user;

import com.edu.tks.exception.NotFoundException;
import com.edu.tks.user.User;
import model.user.UserSOAPEntity;

import java.util.List;

public interface SOAPGetUsers {
    List<UserSOAPEntity> getAllUsers();
    UserSOAPEntity getUserByID(String userid) throws NotFoundException;
    UserSOAPEntity getUserByLogin(String login);
    List<UserSOAPEntity> getUsersByLogin(String login);
}
