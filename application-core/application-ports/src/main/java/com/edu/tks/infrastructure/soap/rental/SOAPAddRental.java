package com.edu.tks.infrastructure.soap.rental;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import model.exceptions.SOAPInputException;
import model.rental.RentalSOAPEntity;

import java.util.List;

public interface SOAPAddRental {
    RentalSOAPEntity createRental(String userID, String recordID) throws InputException, NotFoundException, SOAPInputException;
}
