package com.edu.tks.rental;

import com.edu.tks.exception.NotFoundException;
import com.edu.tks.rental.Rental;

import java.util.List;

public interface GetRentalsUseCase {
    List<Rental> getAllRentals();
    List<Rental> getAllArchiveRentals();
    Rental getRentalByID(String rentalID) throws NotFoundException;
}
