package com.edu.tks.ports.soap.service.rental;

import com.edu.tks.exception.SOAPInputException;
import com.edu.tks.exception.SOAPNotFoundException;
import com.edu.tks.model.rental.RentalSOAP;

public interface SOAPAddRental {
    RentalSOAP createRental(String userID, String recordID) throws SOAPInputException, SOAPNotFoundException;
}
