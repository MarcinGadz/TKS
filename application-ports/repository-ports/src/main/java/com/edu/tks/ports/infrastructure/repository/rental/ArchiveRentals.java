package com.edu.tks.ports.infrastructure.repository.rental;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.rental.Rental;

public interface ArchiveRentals {
    Rental archiveRental(String rentalID) throws NotFoundException, InputException;
}
