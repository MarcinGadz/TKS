package com.edu.tks.user;

import com.edu.tks.exception.BasicException;
import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserWebservice {

    private final UserService userService;

    @Autowired
    public UserWebservice(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public List<User> getUsers(@RequestParam(required = false) String login) throws CloneNotSupportedException {
        if (login != null) {
            return userService.getUsersByLogin(login);
        } else {
            return userService.getAllUsers();
        }
    }


    @GetMapping(path = "/{userID}")
    public User getUserById(@PathVariable("userID") String userID) throws NotFoundException {
        return userService.getUserByID(userID);
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
            userService.appendUser(user);

            return user;
        } catch (NullPointerException
                | IllegalArgumentException
                | IllegalStateException e) {
            throw new InputException("Login Invalid");
        }
    }


    @DeleteMapping("/{userID}")
    public User deleteUser(@PathVariable("userID") String userID) throws BasicException {
        User user = userService.getUserByID(userID);
        userService.removeUser(userID);
        return user;
    }


    @PostMapping(path = "/{userID}/changeLogin")
    public User changeUserLogin(@PathVariable String userID, @RequestBody User body) throws InputException,
            NotFoundException {

        String login = body.getLogin();
        if (!login.matches("^[a-zA-Z0-9_-]{8,16}$")) {
            throw new InputException("Login must be between 8 and 16 characters");
        }

        User user = userService.getUserByID(userID);
        userService.setUserLogin(userID, login);

        return user;
    }


    @PostMapping(path = "/{userID}/activate")
    public User activateUser(@PathVariable String userID) throws NotFoundException, InputException {
        User user = userService.getUserByID(userID);
        user.activate();

        return user;
    }


    @PostMapping(path = "/{userID}/deactivate")
    public User deactivateUser(@PathVariable String userID) throws NotFoundException, InputException {
        User user = userService.getUserByID(userID);
        user.deactivate();

        return user;
    }

}
