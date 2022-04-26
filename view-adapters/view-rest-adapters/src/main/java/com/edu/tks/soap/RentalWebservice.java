package com.edu.tks.soap;

import com.edu.tks.exception.SOAPInputException;
import com.edu.tks.exception.SOAPNotFoundException;
import com.edu.tks.model.RentalView;
import com.edu.tks.ports.view.service.rental.AddRentalUseCase;
import com.edu.tks.ports.view.service.rental.ArchiveRentalsUseCase;
import com.edu.tks.ports.view.service.rental.GetRentalsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("users")
public class RentalWebservice {

    private final AddRentalUseCase addRentalUseCase;
    private final GetRentalsUseCase getRentalsUseCase;
    private final ArchiveRentalsUseCase archiveRentalsUseCase;

    @Autowired
    public RentalWebservice(
            AddRentalUseCase addRentalUseCase,
            GetRentalsUseCase getRentalsUseCase,
            ArchiveRentalsUseCase archiveRentalsUseCase) {
        this.addRentalUseCase = addRentalUseCase;
        this.getRentalsUseCase = getRentalsUseCase;
        this.archiveRentalsUseCase = archiveRentalsUseCase;
    }

    @GetMapping(path = "/rentals")
    public List<RentalView> getAllRentals() {
        return getRentalsUseCase.getAllRentals();
    }


    @GetMapping(path = "/archiveRentals")
    public List<RentalView> getAllArchiveRentals() {
        return getRentalsUseCase.getAllArchiveRentals();
    }

    @PostMapping(path = "/{userID}/rent")
    public RentalView rentRecord(@PathVariable String userID, @RequestBody Map<String, String> body) throws SOAPNotFoundException, SOAPInputException {
        return addRentalUseCase.createRental(userID, body.get("recordID"));
    }

    @PostMapping(path = "/{userID}/return")
    public RentalView returnRecord(@PathVariable String userID, @RequestBody Map<String, String> body) throws SOAPNotFoundException, SOAPInputException {
        RentalView rental = getRentalsUseCase.getRentalByID(body.get("rentalID"));

        if (!rental.getClientID().equals(userID)) {
            System.out.println("THIS USER DOES NOT OWN THIS RECORD");
            throw new SOAPInputException("This user does not own this record!");
        }
        return archiveRentalsUseCase.archiveRental(body.get("rentalID"));
    }
}


