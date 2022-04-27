package com.edu.tks.rest;

import com.edu.tks.rest.exception.InputExceptionView;
import com.edu.tks.rest.exception.NotFoundExceptionView;
import com.edu.tks.rest.model.RentalView;
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
    public RentalView rentRecord(@PathVariable String userID, @RequestBody Map<String, String> body) throws NotFoundExceptionView, InputExceptionView {
        return addRentalUseCase.createRental(userID, body.get("recordID"));
    }

    @PostMapping(path = "/{userID}/return")
    public RentalView returnRecord(@PathVariable String userID, @RequestBody Map<String, String> body) throws NotFoundExceptionView, InputExceptionView {
        RentalView rental = getRentalsUseCase.getRentalByID(body.get("rentalID"));

        if (!rental.getClientID().equals(userID)) {
            System.out.println("THIS USER DOES NOT OWN THIS RECORD");
            throw new InputExceptionView("This user does not own this record!");
        }
        return archiveRentalsUseCase.archiveRental(body.get("rentalID"));
    }
}


