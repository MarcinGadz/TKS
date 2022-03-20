package com.edu.tks.rental;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.PermissionException;
import com.edu.tks.exception.RentalException;
import com.edu.tks.record.Record;
import com.edu.tks.record.RecordService;
import com.edu.tks.user.User;
import com.edu.tks.user.UserService;

import java.util.List;

@RestController
@RequestMapping("users")
public class RentWebservice {

    private final UserService userService;
    private final RecordService recordManager;
    private final RentalService rentalService;

    @Autowired
    public RentWebservice(UserService userService, RecordService recordManager, RentalService rentalService) {
        this.userService = userService;
        this.recordManager = recordManager;
        this.rentalService = rentalService;
    }

    @GetMapping(path = "/rentals")
    public List<Rental> getAllRentals() {
        return rentalService.getAllRentals();
    }


    @GetMapping(path = "/archiveRentals")
    public List<Rental> getAllArchiveRentals() {
        return rentalService.getAllArchiveRentals();
    }

    @GetMapping(path = "/{userID}/cart")
    public List<Record> getCart(@PathVariable String userID) throws NotFoundException {
        User user = userService.getUserByID(userID);
        return user.getCart();
    }

    @PostMapping(path= "/{userID}/cart")
    public List<Record> addRentToCart(@PathVariable("userID") String userID, @RequestBody String body) throws NotFoundException,
                                                                                                    RentalException
    {
        JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
        String recordID = jsonBody.get("recordID").getAsString();

        User user = userService.getUserByID(userID);
        user.addToCart(recordManager.getRecordByID(recordID));
        return user.getCart();
    }

    @DeleteMapping(path = "/{userID}/cart/{recordID}")
    public List<Record> removeRentFromCart(@PathVariable("userID") String userID,
                                            @PathVariable("recordID") String recordID) throws NotFoundException,
                                                                                              RentalException
    {
            User user = userService.getUserByID(userID);
            Record record = recordManager.getRecordByID(recordID);
            user.removeFromCart(record);
            return user.getCart();
    }

    @DeleteMapping(path = "/{userID}/cart")
    public List<Record> removeAllFromCart(@PathVariable("userID") String userID) throws NotFoundException,
            RentalException {
        User user = userService.getUserByID(userID);
        user.clearCart();
        return user.getCart();
    }

    @GetMapping(path = "/{userID}/rentals")
    public List<Rental> getRents(@PathVariable("userID") String userID) throws NotFoundException {
        User user = userService.getUserByID(userID);
        return user.getRentals();
    }

    @GetMapping(path = "/{userID}/rentals/archive")
    public List<Rental> getRentsArchive(@PathVariable("userID") String userID) throws NotFoundException {
        User user = userService.getUserByID(userID);
        return user.getArchiveRentals();
    }

    @PostMapping(path = "/{userID}/rentals")
    public List<Rental> submitRentsFromCart(@PathVariable("userID") String userID, @RequestBody String body) throws NotFoundException, PermissionException, RentalException, InputException {
        JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();

        User user = userService.getUserByID(userID);

        User renter = userService.getUserByID(jsonBody.get("renterID").getAsString());
        List<Rental> newRentals = user.rentCart(renter);
        rentalService.appendRentals(newRentals);

        return user.getRentals();
    }

    @PostMapping(path = "/{userID}/rentals/clear")
    public List<Rental> clearUserRentals(@PathVariable("userID") String userID,
                                         @RequestBody String body) throws NotFoundException,
            PermissionException,
            RentalException, InputException {
        JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();

        User user = userService.getUserByID(userID);
        User renter = userService.getUserByID(jsonBody.get("renterID").getAsString());

        rentalService.archiveRentals(user.getRentals());
        user.clearRentals(renter);
        return user.getRentals();
    }

    @PostMapping(path = "/{userID}/rentals/extend")
    public List<Rental> extendRentReturnDays(@PathVariable("userID") String userID,
                                             @RequestBody String body) throws NotFoundException,
                                                                              PermissionException,
                                                                              RentalException
    {
        JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
        User user = userService.getUserByID(userID);
        String renter = jsonBody.get("renterID").getAsString();
        int days = Integer.parseInt(jsonBody.get("days").toString());

        userService.extendRentReturnDays(renter, userID, days);
        return user.getRentals();
    }

}


