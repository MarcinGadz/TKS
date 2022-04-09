package com.edu.tks.infrastructure.soap.rental;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import model.rental.RentalSOAPEntity;

public interface SOAPArchiveRentals {
    RentalSOAPEntity archiveRental(String rentalID) throws NotFoundException, InputException;
}
