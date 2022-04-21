package com.edu.tks.ports.aggregates.converters.soap;

import com.edu.tks.model.user.UserSOAP;
import com.edu.tks.model.user.UserTypeSOAP;
import com.edu.tks.user.User;
import com.edu.tks.user.UserType;

import java.util.UUID;

public class UserSOAPConverter {

    private static UserTypeSOAP convertUserTypeToUserTypeSOAP(UserType userType) {
        switch (userType) {
            case CLIENT: return UserTypeSOAP.CLIENT;
            case ADMINISTRATOR: return UserTypeSOAP.ADMINISTRATOR;
        }
        return null;
    }

    public static UserSOAP convertUserToUserSOAP(User user) {
        if (user == null) return null;
        UserSOAP userSOAP = new UserSOAP();
        userSOAP.setUserID(user.getUserID().toString());
        userSOAP.setLogin(user.getLogin());
        userSOAP.setActive(user.isActive());
        return userSOAP;
    }

    private static UserType convertUserTypeSOAPToUserType(UserTypeSOAP userTypeSOAP) {
        switch (userTypeSOAP) {
            case CLIENT: return UserType.CLIENT;
            case ADMINISTRATOR: return UserType.ADMINISTRATOR;
        }
        return null;
    }

    public static User convertUserSOAPToUser(UserSOAP userSOAP) {
        if (userSOAP == null) return null;
        return new User(
                UUID.fromString(userSOAP.getUserID()),
                userSOAP.getLogin(),
                userSOAP.isActive(),
                convertUserTypeSOAPToUserType(userSOAP.getType())
        );
    }

}
