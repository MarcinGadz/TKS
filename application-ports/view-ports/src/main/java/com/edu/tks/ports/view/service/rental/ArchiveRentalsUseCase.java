package com.edu.tks.ports.view.service.rental;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.model.RentalView;

public interface ArchiveRentalsUseCase {
    RentalView archiveRental(String rentalID) throws NotFoundException, InputException;
}
