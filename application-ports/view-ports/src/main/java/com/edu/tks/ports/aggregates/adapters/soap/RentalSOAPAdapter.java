package com.edu.tks.ports.aggregates.adapters.soap;

import com.edu.tks.exception.SOAPInputException;
import com.edu.tks.exception.SOAPNotFoundException;
import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.model.rental.RentalSOAP;
import com.edu.tks.ports.aggregates.converters.soap.RentalSOAPConverter;
import com.edu.tks.ports.soap.service.rental.SOAPAddRental;
import com.edu.tks.ports.soap.service.rental.SOAPArchiveRentals;
import com.edu.tks.ports.soap.service.rental.SOAPGetRentals;
import com.edu.tks.service.rental.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentalSOAPAdapter implements SOAPAddRental, SOAPGetRentals, SOAPArchiveRentals {

    @Autowired
    private RentalService rentalService;

    @Override
    public RentalSOAP createRental(String userID, String recordID) throws SOAPInputException, SOAPNotFoundException {
        try {
            return RentalSOAPConverter.convertRentalToRentalSOAP(rentalService.createRental(userID, recordID));
        } catch (InputException e) {
            throw new SOAPInputException(e.getMessage());
        } catch (NotFoundException e) {
            throw new SOAPNotFoundException(e.getMessage());
        }
    }

    @Override
    public RentalSOAP archiveRental(String rentalID) throws SOAPInputException, SOAPNotFoundException {
        try {
            return RentalSOAPConverter.convertRentalToRentalSOAP(rentalService.archiveRental(rentalID));
        } catch (InputException e) {
            throw new SOAPInputException(e.getMessage());
        } catch (NotFoundException e) {
            throw new SOAPNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<RentalSOAP> getAllRentals() {
        return rentalService.getAllRentals().stream()
                .map(RentalSOAPConverter::convertRentalToRentalSOAP)
                .collect(Collectors.toList());
    }

    @Override
    public List<RentalSOAP> getAllArchiveRentals() {
        return rentalService.getAllArchiveRentals().stream()
                .map(RentalSOAPConverter::convertRentalToRentalSOAP)
                .collect(Collectors.toList());
    }

    @Override
    public RentalSOAP getRentalByID(String rentalID) throws SOAPNotFoundException {
        try {
            return RentalSOAPConverter.convertRentalToRentalSOAP(rentalService.getRentalByID(rentalID));
        } catch (NotFoundException e) {
            throw new SOAPNotFoundException(e.getMessage());
        }
    }

}
