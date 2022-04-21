package com.edu.tks.ports.aggregates.converters.view;

import com.edu.tks.model.UserTypeView;
import com.edu.tks.model.UserView;
import com.edu.tks.user.User;
import com.edu.tks.user.UserType;

public class UserViewConverter {
    public static UserType convertUserTypeViewToUserType(UserTypeView userTypeView) {
        switch (userTypeView) {
            case CLIENT: return UserType.CLIENT;
            case ADMINISTRATOR: return UserType.ADMINISTRATOR;
        }
        return null;
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
        switch (userType) {
            case CLIENT: return UserTypeView.CLIENT;
            case ADMINISTRATOR: return UserTypeView.ADMINISTRATOR;
        }
        return null;
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
