package com.edu.tks.repositories;

import com.edu.tks.entity.UserEntity;
import com.edu.tks.entity.UserTypeEntity;
import com.edu.tks.exception.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
    private final List<UserEntity> users;

    public UserRepository() {
        UserEntity[] arr = {
                new UserEntity(UUID.fromString("cebbee82-2398-4dc2-a94d-a4c863286ff0"), "Eleanora", true, UserTypeEntity.CLIENT),
                new UserEntity(UUID.fromString("bbe979ba-ed9c-4028-8c18-97740c25ae99"), "Jason", false, UserTypeEntity.CLIENT),
                new UserEntity(UUID.fromString("c6f3fbf7-135b-498e-8154-3a5b9d291145"), "Chidi", true, UserTypeEntity.CLIENT),
                new UserEntity(UUID.fromString("f6abb370-5dbb-4473-bf8d-505bdcf6ccce"), "Tahani", true, UserTypeEntity.CLIENT),
                new UserEntity(UUID.fromString("73d2c12b-179e-4700-879d-8e3de56fe55f"), "Michael", true, UserTypeEntity.ADMINISTRATOR),
                new UserEntity(UUID.fromString("bfac0aaa-e7d2-4e33-9fa9-8111b14bcd58"), "DiscoJanet", true, UserTypeEntity.CLIENT)
        };
        this.users = new ArrayList<>(Arrays.asList(arr));
    }

    public List<UserEntity> getAllUsers() {
        return users;
    }

    public UserEntity getUserByID(String userid) throws NotFoundException {
        UserEntity user = users.stream()
                .filter(u -> u.getUserID().toString().equals(userid))
                .findFirst()
                .orElse(null);
        if (user == null) {
            throw new NotFoundException("shop.UserEntity not found");
        }
        return user;
    }

    public UserEntity getUserByLogin(String login) {
        return users.stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst()
                .orElse(null);
    }

    public List<UserEntity> getUsersByLogin(String login) {
        return users.stream()
                .filter(user -> user.getLogin().contains(login))
                .collect(Collectors.toList());
    }

    public void appendUser(UserEntity user) throws InputException {
        if (this.getUserByLogin(user.getLogin()) != null) {
            throw new InputException("This login already exists");
        }

        users.add(user);
    }

    public UserEntity removeUser(String userid) throws BasicException {
        UserEntity user = this.getUserByID(userid);
        users.remove(user);
        return user;
    }

    public void extendRentReturnDays(String renterId, String userId, int days) throws PermissionException, RentalException, NotFoundException {
        UserEntity renter = this.getUserByID(renterId);
        UserEntity user = this.getUserByID(userId);
        user.extendRentReturnDays(renter, days);
    }

    public UserEntity updateUserLogin(String userid, String newLogin) {
        UserEntity user = null;
        try {
            user = getUserByID(userid);
            user.setLogin(newLogin);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public UserEntity updateActive(String userId, boolean active) {
        UserEntity user = null;
        try {
            user = getUserByID(userId);
            user.setActive(active);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
}
