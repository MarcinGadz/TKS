package shop.Rental;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shop.Exceptions.InputException;
import shop.Exceptions.NotFoundException;
import shop.Exceptions.PermissionException;
import shop.Exceptions.RentalException;
import shop.Record.Record;
import shop.Record.RecordManager;
import shop.User.User;
import shop.User.UserManager;

import java.util.List;

@RestController
@RequestMapping("users")
public class RentWebservice {

    private final UserManager userManager;
    private final RecordManager recordManager;
    private final RentalManager rentalManager;

    @Autowired
    public RentWebservice(UserManager userManager, RecordManager recordManager, RentalManager rentalManager) {
        this.userManager = userManager;
        this.recordManager = recordManager;
        this.rentalManager = rentalManager;
    }

    @GetMapping(path = "/rentals")
    public List<Rental> getAllRentals() {
        return rentalManager.getAllRentals();
    }


    @GetMapping(path = "/archiveRentals")
    public List<Rental> getAllArchiveRentals() {
        return rentalManager.getAllArchiveRentals();
    }

    @GetMapping(path = "/{userID}/cart")
    public List<Record> getCart(@PathVariable String userID) throws NotFoundException {
        User user = userManager.getUserByID(userID);
        return user.getCart();
    }

    @PostMapping(path= "/{userID}/cart")
    public List<Record> addRentToCart(@PathVariable("userID") String userID, @RequestBody String body) throws NotFoundException,
                                                                                                    RentalException
    {
        JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
        String recordID = jsonBody.get("recordID").getAsString();

        User user = userManager.getUserByID(userID);
        user.addToCart(recordManager.getRecordByID(recordID));
        return user.getCart();
    }

    @DeleteMapping(path = "/{userID}/cart/{recordID}")
    public List<Record> removeRentFromCart(@PathVariable("userID") String userID,
                                            @PathVariable("recordID") String recordID) throws NotFoundException,
                                                                                              RentalException
    {
            User user = userManager.getUserByID(userID);
            Record record = recordManager.getRecordByID(recordID);
            user.removeFromCart(record);
            return user.getCart();
    }

    @DeleteMapping(path = "/{userID}/cart")
    public List<Record> removeAllFromCart(@PathVariable("userID") String userID) throws NotFoundException,
            RentalException {
        User user = userManager.getUserByID(userID);
        user.clearCart();
        return user.getCart();
    }

    @GetMapping(path = "/{userID}/rentals")
    public List<Rental> getRents(@PathVariable("userID") String userID) throws NotFoundException {
        User user = userManager.getUserByID(userID);
        return user.getRentals();
    }

    @GetMapping(path = "/{userID}/rentals/archive")
    public List<Rental> getRentsArchive(@PathVariable("userID") String userID) throws NotFoundException {
        User user = userManager.getUserByID(userID);
        return user.getArchiveRentals();
    }

    @PostMapping(path = "/{userID}/rentals")
    public List<Rental> submitRentsFromCart(@PathVariable("userID") String userID, @RequestBody String body) throws NotFoundException, PermissionException, RentalException, InputException {
        JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();

        User user = userManager.getUserByID(userID);

        User renter = userManager.getUserByID(jsonBody.get("renterID").getAsString());
        List<Rental> newRentals = user.rentCart(renter);
        rentalManager.appendRentals(newRentals);

        return user.getRentals();
    }

    @PostMapping(path = "/{userID}/rentals/clear")
    public List<Rental> clearUserRentals(@PathVariable("userID") String userID,
                                         @RequestBody String body) throws NotFoundException,
            PermissionException,
            RentalException, InputException {
        JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();

        User user = userManager.getUserByID(userID);
        User renter = userManager.getUserByID(jsonBody.get("renterID").getAsString());

        rentalManager.archiveRentals(user.getRentals());
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
        User user = userManager.getUserByID(userID);
        String renter = jsonBody.get("renterID").getAsString();
        int days = Integer.parseInt(jsonBody.get("days").toString());

        userManager.extendRentReturnDays(renter, userID, days);
        return user.getRentals();
    }

}


