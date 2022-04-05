package com.edu.tks.aggregates.adapters.repository;

import com.edu.tks.exception.*;
import com.edu.tks.infrastructure.repository.user.AddUser;
import com.edu.tks.infrastructure.repository.user.ExtendRentals;
import com.edu.tks.infrastructure.repository.user.GetUsers;
import com.edu.tks.infrastructure.repository.user.RemoveUser;
import com.edu.tks.repositories.UserRepository;
import com.edu.tks.user.User;
import com.edu.tks.aggregates.converters.repository.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRepositoryAdapter implements AddUser, GetUsers, ExtendRentals, RemoveUser {

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
        repo.appendUser(UserConverter.convertUserToUserEntity(user));
    }

    @Override
    public void extendRentReturnDays(String renterId, String userId, int days) throws PermissionException, RentalException, NotFoundException {
        repo.extendRentReturnDays(renterId, userId, days);
    }

    @Override
    public List<User> getAllUsers() {
        return repo.getAllUsers().stream()
                .map(UserConverter::convertUserEntityToUser)
                .collect(Collectors.toList());
    }

    @Override
    public User getUserByID(String userid) throws NotFoundException {
        return UserConverter.convertUserEntityToUser(repo.getUserByID(userid));
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
    public void removeUser(String userid) throws BasicException {
        repo.removeUser(userid);
    }
}
