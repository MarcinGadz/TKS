package com.edu.tks.aggregates.converters.repository;

import com.edu.tks.entity.UserEntity;
import com.edu.tks.entity.UserTypeEntity;
import com.edu.tks.user.User;
import com.edu.tks.user.UserType;

public class UserConverter {

    public static UserType convertUserTypeEntityToUserType(UserTypeEntity userTypeEntity) {
        return switch (userTypeEntity) {
            case CLIENT -> UserType.CLIENT;
            case RENTER -> UserType.RENTER;
            case ADMINISTRATOR -> UserType.ADMINISTRATOR;
        };
    }

    public static User convertUserEntityToUser(UserEntity userEntity) {
        if (userEntity == null) return null;
        return new User(
                userEntity.getUserID(),
                userEntity.getLogin(),
                userEntity.isActive(),
                convertUserTypeEntityToUserType(userEntity.getType())
        );
    }

    public static UserTypeEntity convertUserTypeToUserTypeEntity(UserType userType) {
        return switch (userType) {
            case CLIENT -> UserTypeEntity.CLIENT;
            case RENTER -> UserTypeEntity.RENTER;
            case ADMINISTRATOR -> UserTypeEntity.ADMINISTRATOR;
        };
    }

    public static UserEntity convertUserToUserEntity(User user) {
        if (user == null) return null;
        return new UserEntity(
                user.getUserID(),
                user.getLogin(),
                user.isActive(),
                convertUserTypeToUserTypeEntity(user.getType())
        );
    }
}