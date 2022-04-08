package com.edu.tks.rental;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;

public interface AddRentalUseCase {
    Rental createRental(String clientID, String recordID) throws InputException, NotFoundException;
}
