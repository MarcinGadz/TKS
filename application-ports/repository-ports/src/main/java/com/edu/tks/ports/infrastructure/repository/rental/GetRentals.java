package com.edu.tks.ports.infrastructure.repository.rental;

import com.edu.tks.exception.NotFoundException;
import com.edu.tks.rental.Rental;

import java.util.List;

public interface GetRentals {
    List<Rental> getAllRentals();
    List<Rental> getAllArchiveRentals();
    Rental getRentalByID(String rentalID) throws NotFoundException;
}
