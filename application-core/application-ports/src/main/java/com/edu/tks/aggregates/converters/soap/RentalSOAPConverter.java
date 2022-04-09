package com.edu.tks.aggregates.converters.soap;

import com.edu.tks.exception.InputException;
import com.edu.tks.rental.Rental;
import model.exceptions.SOAPInputException;
import model.rental.RentalSOAPEntity;

import java.util.UUID;

public class RentalSOAPConverter {

    public static RentalSOAPEntity convertRentalToRentalSOAPEntity(Rental rental) throws SOAPInputException {
        if (rental == null) return null;
        return new RentalSOAPEntity(
                rental.getRentalID(),
                UUID.fromString(rental.getClientID()),
                UUID.fromString(rental.getRecordID()),
                rental.getRentDate(),
                rental.getExpectedReturnDate(),
                rental.getActualReturnDate(),
                rental.isActive()
        );
    }

    public static Rental convertRentalSOAPEntityToRental(RentalSOAPEntity rentalSOAP) throws InputException {
        if (rentalSOAP == null) return null;
        return new Rental(
                rentalSOAP.getRentalID(),
                rentalSOAP.getClientID().toString(),
                rentalSOAP.getRecordID().toString(),
                rentalSOAP.getRentDate(),
                rentalSOAP.getExpectedReturnDate(),
                rentalSOAP.getActualReturnDate(),
                rentalSOAP.isActive()
        );
    }

}
