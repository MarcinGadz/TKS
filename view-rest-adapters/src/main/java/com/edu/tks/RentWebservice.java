package com.edu.tks;

import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.RentalException;
import com.edu.tks.record.RecordService;
import com.edu.tks.rental.RentalService;
import com.edu.tks.user.User;
import com.edu.tks.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "/{userID}/cart")
    public List<Record> addRentToCart(@PathVariable("userID") String userID, @RequestBody Record body) throws NotFoundException,
            RentalException {
        String recordID = body.getRecordID().toString();
        User user = userService.getUserByID(userID);
        user.addToCart(recordManager.getRecordByID(recordID));
        return user.getCart();
    }

    @DeleteMapping(path = "/{userID}/cart/{recordID}")
    public List<Record> removeRentFromCart(@PathVariable("userID") String userID,
                                           @PathVariable("recordID") String recordID) throws NotFoundException,
            RentalException {
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


}


