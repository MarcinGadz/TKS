package com.edu.tks.infrastructure.repository.rental;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.rental.Rental;

import java.util.List;

public interface ArchiveRentals {
    void archiveRental(String rentalID) throws NotFoundException, InputException;
    void archiveRentals(List<Rental> rents) throws InputException;
}
