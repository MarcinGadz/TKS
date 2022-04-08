package com.edu.tks.rental;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.user.User;
import com.edu.tks.record.Record;

public interface AddRentalUseCase {
    Rental createRental(User client, Record record) throws InputException, NotFoundException;
}
