package com.edu.tks;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.RentalException;
import com.edu.tks.record.AddRecordUseCase;
import com.edu.tks.record.GetRecordsUseCase;
import com.edu.tks.record.Record;
import com.edu.tks.record.RemoveRecordUseCase;
import com.edu.tks.rental.AddRentalUseCase;
import com.edu.tks.rental.ArchiveRentalsUseCase;
import com.edu.tks.rental.GetRentalsUseCase;
import com.edu.tks.rental.Rental;
import com.edu.tks.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("users")
public class RentWebservice {

    private final AddRentalUseCase addRentalUseCase;
    private final GetRentalsUseCase getRentalsUseCase;
    private final ArchiveRentalsUseCase archiveRentalsUseCase;

    @Autowired
    public RentWebservice(
            AddRentalUseCase addRentalUseCase,
            GetRentalsUseCase getRentalsUseCase,
            ArchiveRentalsUseCase archiveRentalsUseCase) {
        this.addRentalUseCase = addRentalUseCase;
        this.getRentalsUseCase = getRentalsUseCase;
        this.archiveRentalsUseCase = archiveRentalsUseCase;
    }

    @GetMapping(path = "/rentals")
    public List<Rental> getAllRentals() {
        return getRentalsUseCase.getAllRentals();
    }


    @GetMapping(path = "/archiveRentals")
    public List<Rental> getAllArchiveRentals() {
        return getRentalsUseCase.getAllArchiveRentals();
    }

    @PostMapping(path = "/{userID}/rent")
    public Rental rentRecord(@PathVariable String userID, @RequestBody Map<String, String> body) throws NotFoundException, InputException {
        return addRentalUseCase.createRental(userID, body.get("recordID"));
    }

    @PostMapping(path = "/{userID}/return")
    public Rental returnRecord(@PathVariable String userID, @RequestBody Map<String, String> body) throws NotFoundException, InputException {
        Rental rental = getRentalsUseCase.getRentalByID(body.get("rentalID"));

        if (!rental.getClientID().equals(userID)) {
            System.out.println("THIS USER DOES NOT OWN THIS RECORD");
            throw new InputException("This user does not own this record!");
        }
        return archiveRentalsUseCase.archiveRental(body.get("rentalID"));
    }
}


