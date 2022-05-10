package com.edu.tks.ports.soap.service.rental;

import com.edu.tks.exception.SOAPInputException;
import com.edu.tks.exception.SOAPNotFoundException;
import com.edu.tks.model.rental.RentalSOAP;

public interface SOAPArchiveRentals {
    RentalSOAP archiveRental(String rentalID) throws SOAPNotFoundException, SOAPInputException;
}
