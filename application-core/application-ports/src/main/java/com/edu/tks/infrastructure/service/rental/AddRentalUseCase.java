package com.edu.tks.infrastructure.service.rental;

import com.edu.tks.rental.Rental;

import java.util.List;

public interface AddRentalUseCase {
    void appendRental(Rental rental);
    void appendRentals(List<Rental> rents);
}
