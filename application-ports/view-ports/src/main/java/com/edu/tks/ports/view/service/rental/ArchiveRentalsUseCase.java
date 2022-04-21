package com.edu.tks.ports.view.service.rental;

import com.edu.tks.exception.SOAPInputException;
import com.edu.tks.exception.SOAPNotFoundException;
import com.edu.tks.model.RentalView;

public interface ArchiveRentalsUseCase {
    RentalView archiveRental(String rentalID) throws SOAPInputException, SOAPNotFoundException;
}
