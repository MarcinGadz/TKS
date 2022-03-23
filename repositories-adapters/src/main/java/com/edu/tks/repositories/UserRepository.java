package com.edu.tks.repositories;
import com.edu.tks.entity.UserEntity;
import com.edu.tks.entity.UserTypeEntity;
import com.edu.tks.exception.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
    private final List<UserEntity> users;

    public UserRepository() {
        UserEntity[] arr = {
                new UserEntity( "Eleanor", UserTypeEntity.CLIENT),
                new UserEntity("Jason", UserTypeEntity.CLIENT),
                new UserEntity("Chidi", UserTypeEntity.CLIENT),
                new UserEntity("Tahani", UserTypeEntity.CLIENT),
                new UserEntity("Michael", UserTypeEntity.ADMINISTRATOR),
                new UserEntity("DiscoJanet", UserTypeEntity.RENTER)
        };
        this.users = new ArrayList<>(Arrays.asList(arr));
    }

    public List<UserEntity> getAllUsers() {
        return users;
    }

    public UserEntity getUserByID(String userid) throws NotFoundException {
        UserEntity user = users.stream()
                .filter( u -> u.getUserID().toString().equals(userid))
                .findFirst()
                .orElse(null);
        if (user == null) {
            throw new NotFoundException("shop.UserEntity not found");
        }
        return user;
    }

    public UserEntity getUserByLogin(String login) {
        return users.stream()
                .filter( user -> user.getLogin().equals(login))
                .findFirst()
                .orElse(null);
    }

    public List<UserEntity> getUsersByLogin(String login) {
        return users.stream()
                .filter( user -> user.getLogin().contains(login))
                .collect(Collectors.toList());
    }

    public void appendUser(UserEntity user) throws InputException {
        if (this.getUserByLogin(user.getLogin()) != null) {
            throw new InputException("This login already exists");
        }

        users.add(user);
    }

    public void removeUser(String userid) throws BasicException {
        UserEntity user = this.getUserByID(userid);

        users.remove(user);
    }

    public void extendRentReturnDays(String renterId, String userId, int days) throws PermissionException, RentalException, NotFoundException {
       UserEntity renter = this.getUserByID(renterId);
       UserEntity user = this.getUserByID(userId);
       user.extendRentReturnDays(renter, days);
    }
}
