package com.edu.tks;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.RentalException;
import com.edu.tks.record.Record;
import com.edu.tks.record.RecordService;
import com.edu.tks.rental.Rental;
import com.edu.tks.rental.RentalService;
import com.edu.tks.user.User;
import com.edu.tks.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @PostMapping(path = "/{userID}/rent")
    public Rental rentRecords(@PathVariable String userID, @RequestBody Map<String, String> body) throws NotFoundException, InputException {
        return rentalService.createRental(userID, body.get("recordID"));

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


