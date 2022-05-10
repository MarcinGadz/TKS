package com.edu.tks.ports.soap.service.rental;

import com.edu.tks.exception.SOAPInputException;
import com.edu.tks.exception.SOAPNotFoundException;
import com.edu.tks.model.rental.RentalSOAP;

import java.util.List;

public interface SOAPGetRentals {
    List<RentalSOAP> getAllRentals() throws SOAPInputException;
    List<RentalSOAP> getAllArchiveRentals();
    RentalSOAP getRentalByID(String rentalID) throws SOAPNotFoundException;
}
