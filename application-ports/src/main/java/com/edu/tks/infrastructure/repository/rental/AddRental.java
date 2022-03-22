package com.edu.tks.infrastructure.repository.rental;

import com.edu.tks.rental.Rental;

import java.util.List;

public interface AddRental {
    void appendRental(Rental rental);
    void appendRentals(List<Rental> rents);
}
