package com.edu.tks.infrastructure.soap.rental;

import com.edu.tks.exception.NotFoundException;
import model.rental.RentalSOAPEntity;

import java.util.List;

public interface SOAPGetRentals {
    List<RentalSOAPEntity> getAllRentals();
    List<RentalSOAPEntity> getAllArchiveRentals();
    RentalSOAPEntity getRentalByID(String rentalID) throws NotFoundException;
}
