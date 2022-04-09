package com.edu.tks.aggregates.converters.soap;

import com.edu.tks.entity.UserTypeEntity;
import com.edu.tks.user.User;
import com.edu.tks.user.UserType;
import model.user.UserSOAPEntity;
import model.user.UserTypeSOAPEntity;

public class UserSOAPConverter {

    private static UserTypeSOAPEntity convertUserTypeToUserTypeSOAPEntity(UserType userType) {
        return switch (userType) {
            case CLIENT -> UserTypeSOAPEntity.CLIENT;
            case ADMINISTRATOR -> UserTypeSOAPEntity.ADMINISTRATOR;
        };
    }

    public static UserSOAPEntity convertUserToUserSOAPEntity(User user) {
        if (user == null) return null;
        return new UserSOAPEntity(
                user.getUserID(),
                user.getLogin(),
                user.isActive(),
                convertUserTypeToUserTypeSOAPEntity(user.getType())
        );
    }

    private static UserType convertUserTypeSOAPEntityToUserType(UserTypeSOAPEntity userTypeSOAP) {
        return switch (userTypeSOAP) {
            case CLIENT -> UserType.CLIENT;
            case ADMINISTRATOR -> UserType.ADMINISTRATOR;
        };
    }

    public static User convertUserSOAPEntityToUser(UserSOAPEntity userSOAP) {
        if (userSOAP == null) return null;
        return new User(
                userSOAP.getUserID(),
                userSOAP.getLogin(),
                userSOAP.isActive(),
                convertUserTypeSOAPEntityToUserType(userSOAP.getType())
        );
    }

}
