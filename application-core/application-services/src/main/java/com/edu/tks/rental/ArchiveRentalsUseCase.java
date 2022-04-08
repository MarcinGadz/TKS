package com.edu.tks.rental;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.rental.Rental;

import java.util.List;

public interface ArchiveRentalsUseCase {
    Rental archiveRental(String rentalID) throws NotFoundException, InputException;
}
