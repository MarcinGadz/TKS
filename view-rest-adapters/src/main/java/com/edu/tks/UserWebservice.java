package com.edu.tks;

import com.edu.tks.exception.*;
import com.edu.tks.user.*;
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
    public List<User> getUsers(@RequestParam(required = false) String login) throws CloneNotSupportedException {
        if (login != null) {
            return getUsersUseCase.getUsersByLogin(login);
        } else {
            return getUsersUseCase.getAllUsers();
        }
    }


    @GetMapping(path = "/{userID}")
    public User getUserById(@PathVariable("userID") String userID) throws NotFoundException {
        return getUsersUseCase.getUserByID(userID);
    }

    @PostMapping
    public User addUser(@RequestBody User body) throws InputException {
        try {
            String login = body.getLogin();

            if (!login.matches("^[a-zA-Z0-9_-]{8,16}$")) {
                throw new InputException("Login must be between 8 and 16 characters");
            }

            UserType type = body.getType();

            User user = new User(login, type);
            addUserUseCase.appendUser(user);

            return user;
        } catch (NullPointerException
                | IllegalArgumentException
                | IllegalStateException e) {
            throw new InputException("Login Invalid");
        }
    }


    @DeleteMapping("/{userID}")
    public User deleteUser(@PathVariable("userID") String userID) throws BasicException {
        User user = getUsersUseCase.getUserByID(userID);
        removeUserUseCase.removeUser(userID);
        return user;
    }


    @PutMapping(path = "/{userID}/changeLogin")
    public User changeUserLogin(@PathVariable String userID, @RequestBody User body) throws InputException,
            NotFoundException {
        String login = body.getLogin();
        if (!login.matches("^[a-zA-Z0-9_-]{8,16}$")) {
            throw new InputException("Login must be between 8 and 16 characters");
        }
        User user = addUserUseCase.updateUserLogin(userID, login);
        return user;
    }


    @PutMapping(path = "/{userID}/activate")
    public User activateUser(@PathVariable String userID) throws NotFoundException, InputException {
        return addUserUseCase.updateActive(userID, true);
    }


    @PutMapping(path = "/{userID}/deactivate")
    public User deactivateUser(@PathVariable String userID) throws NotFoundException, InputException {
        User user = addUserUseCase.updateActive(userID, false);
        return user;
    }

    @PostMapping("/{userId}/extend")
    public void extendRentReturnDays(@PathVariable String userId, @RequestParam Integer days) throws PermissionException, RentalException, NotFoundException {
        extendRentalsUseCase.extendRentReturnDays(userId, days);
    }

}
