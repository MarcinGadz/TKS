package com.edu.tks.aggregates.adapters.soap;

import com.edu.tks.aggregates.converters.soap.RentalSOAPConverter;
import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.infrastructure.soap.rental.SOAPAddRental;
import com.edu.tks.infrastructure.soap.rental.SOAPArchiveRentals;
import com.edu.tks.infrastructure.soap.rental.SOAPGetRentals;
import com.edu.tks.rental.AddRentalUseCase;
import com.edu.tks.rental.ArchiveRentalsUseCase;
import com.edu.tks.rental.GetRentalsUseCase;
import model.rental.RentalSOAPEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class RentalSOAPAdapter implements SOAPAddRental, SOAPGetRentals, SOAPArchiveRentals {

    @Autowired
    private AddRentalUseCase addRentalUseCase;
    @Autowired
    private GetRentalsUseCase getRentalsUseCase;
    @Autowired
    private ArchiveRentalsUseCase archiveRentalsUseCase;

    @Override
    public RentalSOAPEntity createRental(String userID, String recordID) throws InputException, NotFoundException {
        return RentalSOAPConverter.convertRentalToRentalSOAPEntity(addRentalUseCase.createRental(userID, recordID));
    }

    @Override
    public RentalSOAPEntity archiveRental(String rentalID) throws InputException, NotFoundException {
        return RentalSOAPConverter.convertRentalToRentalSOAPEntity(archiveRentalsUseCase.archiveRental(rentalID));
    }

    @Override
    public List<RentalSOAPEntity> getAllRentals() {
        return getRentalsUseCase.getAllRentals().stream()
                .map(RentalSOAPConverter::convertRentalToRentalSOAPEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<RentalSOAPEntity> getAllArchiveRentals() {
        return getRentalsUseCase.getAllArchiveRentals().stream()
                .map(RentalSOAPConverter::convertRentalToRentalSOAPEntity)
                .collect(Collectors.toList());
    }

    @Override
    public RentalSOAPEntity getRentalByID(String rentalID) throws NotFoundException {
        return RentalSOAPConverter.convertRentalToRentalSOAPEntity(getRentalsUseCase.getRentalByID(rentalID));
    }

}
