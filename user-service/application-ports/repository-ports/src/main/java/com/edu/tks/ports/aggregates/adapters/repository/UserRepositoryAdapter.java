package com.edu.tks.ports.aggregates.adapters.repository;

import com.edu.tks.exception.*;
import com.edu.tks.ports.infrastructure.repository.user.AddUser;
import com.edu.tks.ports.infrastructure.repository.user.GetUsers;
import com.edu.tks.ports.infrastructure.repository.user.RemoveUser;
import com.edu.tks.repo.repositories.UserRepository;
import com.edu.tks.user.User;
import com.edu.tks.ports.aggregates.converters.repository.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRepositoryAdapter implements AddUser, GetUsers, RemoveUser {

    @Autowired
    UserRepository repo;

    @Override
    public User updateUserLogin(String userid, String newLogin) {
        return UserConverter.convertUserEntityToUser(repo.updateUserLogin(userid, newLogin));
    }

    @Override
    public User updateActive(String userId, boolean active) {
        return UserConverter.convertUserEntityToUser(repo.updateActive(userId, active));
    }

    @Override
    public void appendUser(User user) throws InputException {
        try {
            repo.appendUser(UserConverter.convertUserToUserEntity(user));
        } catch (com.edu.tks.repo.exception.InputException e) {
            throw new InputException(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        return repo.getAllUsers().stream()
                .map(UserConverter::convertUserEntityToUser)
                .collect(Collectors.toList());
    }

    @Override
    public User getUserByID(String userid) throws NotFoundException {
        try {
            return UserConverter.convertUserEntityToUser(repo.getUserByID(userid));
        } catch (com.edu.tks.repo.exception.NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public User getUserByLogin(String login) {
        return UserConverter.convertUserEntityToUser(repo.getUserByLogin(login));
    }

    @Override
    public List<User> getUsersByLogin(String login) {
        return repo.getUsersByLogin(login).stream()
                .map(UserConverter::convertUserEntityToUser)
                .collect(Collectors.toList());
    }

    @Override
    public User removeUser(String userid) throws BasicException {
        try {
            return UserConverter.convertUserEntityToUser(repo.removeUser(userid));
        } catch (com.edu.tks.repo.exception.BasicException e) {
            throw new BasicException(e.getMessage());
        }
    }
}
