package com.edu.tks.soap;

import com.edu.tks.exception.*;
import com.edu.tks.model.UserTypeView;
import com.edu.tks.model.UserView;
import com.edu.tks.ports.view.service.user.AddUserUseCase;
import com.edu.tks.ports.view.service.user.ExtendRentalsUseCase;
import com.edu.tks.ports.view.service.user.GetUsersUseCase;
import com.edu.tks.ports.view.service.user.RemoveUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserWebservice {

    private final AddUserUseCase addUserUseCase;
    private final GetUsersUseCase getUsersUseCase;
    private final RemoveUserUseCase removeUserUseCase;
    private final ExtendRentalsUseCase extendRentalsUseCase;

    @Autowired
    public UserWebservice(AddUserUseCase addUserUseCase, GetUsersUseCase getUsersUseCase, RemoveUserUseCase removeUserUseCase, ExtendRentalsUseCase extendRentalsUseCase) {
        this.addUserUseCase = addUserUseCase;
        this.getUsersUseCase = getUsersUseCase;
        this.removeUserUseCase = removeUserUseCase;
        this.extendRentalsUseCase = extendRentalsUseCase;
    }

    @GetMapping
    public List<UserView> getUsers(@RequestParam(required = false) String login) {
        if (login != null) {
            return getUsersUseCase.getUsersByLogin(login);
        } else {
            return getUsersUseCase.getAllUsers();
        }
    }


    @GetMapping(path = "/{userID}")
    public UserView getUserById(@PathVariable("userID") String userID) throws SOAPNotFoundException {
        return getUsersUseCase.getUserByID(userID);
    }

    @PostMapping
    public UserView addUser(@RequestBody UserView body) throws SOAPInputException {
        try {
            String login = body.getLogin();

            if (!login.matches("^[a-zA-Z0-9_-]{8,16}$")) {
                throw new SOAPInputException("Login must be between 8 and 16 characters");
            }

            UserTypeView type = body.getType();

            UserView user = new UserView(login, type);
            addUserUseCase.appendUser(user);

            return user;
        } catch (NullPointerException
                | IllegalArgumentException
                | IllegalStateException e) {
            throw new SOAPInputException("Login Invalid");
        }
    }


    @DeleteMapping("/{userID}")
    public UserView deleteUser(@PathVariable("userID") String userID) throws BasicSOAPException, SOAPNotFoundException {
        UserView user = getUsersUseCase.getUserByID(userID);
        removeUserUseCase.removeUser(userID);
        return user;
    }


    @PutMapping(path = "/{userID}/changeLogin")
    public UserView changeUserLogin(@PathVariable String userID, @RequestBody UserView body) throws SOAPInputException,
            SOAPNotFoundException {
        String login = body.getLogin();
        if (!login.matches("^[a-zA-Z0-9_-]{8,16}$")) {
            throw new SOAPInputException("Login must be between 8 and 16 characters");
        }
        UserView user = addUserUseCase.updateUserLogin(userID, login);
        return user;
    }


    @PutMapping(path = "/{userID}/activate")
    public UserView activateUser(@PathVariable String userID) throws SOAPNotFoundException, SOAPInputException {
        return addUserUseCase.updateActive(userID, true);
    }


    @PutMapping(path = "/{userID}/deactivate")
    public UserView deactivateUser(@PathVariable String userID) throws SOAPNotFoundException, SOAPInputException {
        UserView user = addUserUseCase.updateActive(userID, false);
        return user;
    }

    @PostMapping("/{userId}/extend")
    public void extendRentReturnDays(@PathVariable String userId, @RequestParam Integer days) throws SOAPPermissionException, SOAPRentalException, SOAPNotFoundException {
        extendRentalsUseCase.extendRentReturnDays(userId, days);
    }

}
