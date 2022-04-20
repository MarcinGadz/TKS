package com.edu.tks.ports.aggregates.converters.view;

import com.edu.tks.model.UserTypeView;
import com.edu.tks.model.UserView;
import com.edu.tks.user.User;
import com.edu.tks.user.UserType;

public class UserViewConverter {
    public static UserType convertUserTypeViewToUserType(UserTypeView userTypeView) {
        return switch (userTypeView) {
            case CLIENT -> UserType.CLIENT;
            case ADMINISTRATOR -> UserType.ADMINISTRATOR;
        };
    }

    public static User convertUserViewToUser(UserView userView) {
        if (userView == null) return null;
        return new User(
                userView.getUserID(),
                userView.getLogin(),
                userView.isActive(),
                convertUserTypeViewToUserType(userView.getType())
        );
    }

    public static UserTypeView convertUserTypeToUserTypeView(UserType userType) {
        return switch (userType) {
            case CLIENT -> UserTypeView.CLIENT;
            case ADMINISTRATOR -> UserTypeView.ADMINISTRATOR;
        };
    }

    public static UserView convertUserToUserView(User user) {
        if (user == null) return null;
        return new UserView(
                user.getUserID(),
                user.getLogin(),
                user.isActive(),
                convertUserTypeToUserTypeView(user.getType())
        );
    }
}
