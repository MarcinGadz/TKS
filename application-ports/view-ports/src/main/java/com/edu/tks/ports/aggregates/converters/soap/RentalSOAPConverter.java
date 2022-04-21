package com.edu.tks.ports.aggregates.converters.soap;

import com.edu.tks.exception.InputException;
import com.edu.tks.model.rental.RentalSOAP;
import com.edu.tks.ports.utils.DateConversion;
import com.edu.tks.rental.Rental;

import java.util.UUID;

public class RentalSOAPConverter {

    public static RentalSOAP convertRentalToRentalSOAP(Rental rental) {
        if (rental == null) return null;
        RentalSOAP rentalSoap = new RentalSOAP();
        rentalSoap.setRentalID(rental.getRentalID().toString());
        rentalSoap.setRecordID(rental.getClientID());
        rentalSoap.setClientID(rental.getRecordID());
        rentalSoap.setRentDate(DateConversion.convertToXMLGregorianCalendar(rental.getRentDate()));
        rentalSoap.setExpectedReturnDate(DateConversion.convertToXMLGregorianCalendar(rental.getExpectedReturnDate()));
        rentalSoap.setActualReturnDate(DateConversion.convertToXMLGregorianCalendar(rental.getActualReturnDate()));
        rentalSoap.setActive(rental.isActive());
        return rentalSoap;
    }

    public static Rental convertRentalSOAPToRental(RentalSOAP rentalSOAP) throws InputException {
        if (rentalSOAP == null) return null;
        return new Rental(
                UUID.fromString(rentalSOAP.getRentalID()),
                rentalSOAP.getClientID(),
                rentalSOAP.getRecordID(),
                DateConversion.convertToLocalDateTime(rentalSOAP.getRentDate()),
                DateConversion.convertToLocalDateTime(rentalSOAP.getExpectedReturnDate()),
                DateConversion.convertToLocalDateTime(rentalSOAP.getActualReturnDate()),
                rentalSOAP.isActive()
        );
    }

}
