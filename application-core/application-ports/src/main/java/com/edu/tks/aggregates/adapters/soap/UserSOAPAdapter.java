package com.edu.tks.aggregates.adapters.soap;

import com.edu.tks.aggregates.converters.soap.UserSOAPConverter;
import com.edu.tks.exception.BasicException;
import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.infrastructure.soap.user.SOAPAddUser;
import com.edu.tks.infrastructure.soap.user.SOAPGetUsers;
import com.edu.tks.infrastructure.soap.user.SOAPRemoveUser;
import com.edu.tks.user.AddUserUseCase;
import com.edu.tks.user.GetUsersUseCase;
import com.edu.tks.user.RemoveUserUseCase;
import model.user.UserSOAPEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class UserSOAPAdapter implements SOAPAddUser, SOAPGetUsers, SOAPRemoveUser {

    private final AddUserUseCase addUserUseCase;
    private final RemoveUserUseCase removeUserUseCases;
    private final GetUsersUseCase getUsersUseCase;

    @Autowired
    public UserSOAPAdapter(AddUserUseCase addUserUseCase, RemoveUserUseCase removeUserUseCases, GetUsersUseCase getUsersUseCase) {
        this.addUserUseCase = addUserUseCase;
        this.removeUserUseCases = removeUserUseCases;
        this.getUsersUseCase = getUsersUseCase;
    }

    @Override
    public UserSOAPEntity appendUser(UserSOAPEntity user) throws InputException {
         addUserUseCase.appendUser(UserSOAPConverter.convertUserSOAPEntityToUser(user));
         return user;
    }

    @Override
    public UserSOAPEntity updateUserLogin(String userid, String newLogin) throws InputException {
        return UserSOAPConverter.convertUserToUserSOAPEntity(addUserUseCase.updateUserLogin(userid, newLogin));
    }

    @Override
    public UserSOAPEntity updateActive(String userId, boolean active) {
        return UserSOAPConverter.convertUserToUserSOAPEntity(addUserUseCase.updateActive(userId, active));
    }

    @Override
    public List<UserSOAPEntity> getAllUsers() {
        return getUsersUseCase.getAllUsers().stream()
                .map(UserSOAPConverter::convertUserToUserSOAPEntity)
                .collect(Collectors.toList());
    }

    @Override
    public UserSOAPEntity getUserByID(String userid) throws NotFoundException {
        return UserSOAPConverter.convertUserToUserSOAPEntity(getUsersUseCase.getUserByID(userid));
    }

    @Override
    public UserSOAPEntity getUserByLogin(String login) {
        return UserSOAPConverter.convertUserToUserSOAPEntity(getUsersUseCase.getUserByLogin(login));
    }

    @Override
    public List<UserSOAPEntity> getUsersByLogin(String login) {
        return getUsersUseCase.getUsersByLogin(login).stream()
                .map(UserSOAPConverter::convertUserToUserSOAPEntity)
                .collect(Collectors.toList());
    }

    @Override
    public UserSOAPEntity removeUser(String userid) throws BasicException {
        return UserSOAPConverter.convertUserToUserSOAPEntity(removeUserUseCases.removeUser(userid));
    }
}