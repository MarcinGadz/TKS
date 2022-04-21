package com.edu.tks.ports.view.service.rental;

import com.edu.tks.exception.SOAPNotFoundException;
import com.edu.tks.model.RentalView;

import java.util.List;

public interface GetRentalsUseCase {
    List<RentalView> getAllRentals();
    List<RentalView> getAllArchiveRentals();
    RentalView getRentalByID(String rentalID) throws SOAPNotFoundException;
}
