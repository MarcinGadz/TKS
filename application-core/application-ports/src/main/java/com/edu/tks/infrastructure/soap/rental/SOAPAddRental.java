package com.edu.tks.infrastructure.soap.rental;

import com.edu.tks.rental.Rental;
import model.rental.RentalSOAPEntity;

import java.util.List;

public interface SOAPAddRental {
    RentalSOAPEntity appendRental(RentalSOAPEntity rental);
    RentalSOAPEntity appendRentals(List<RentalSOAPEntity> rents);
}
