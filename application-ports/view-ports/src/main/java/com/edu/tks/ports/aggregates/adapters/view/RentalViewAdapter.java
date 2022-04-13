package com.edu.tks.ports.aggregates.adapters.view;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.InputExceptionView;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.NotFoundExceptionView;
import com.edu.tks.model.RentalView;
import com.edu.tks.ports.aggregates.converters.view.RentalViewConverter;
import com.edu.tks.ports.view.service.rental.AddRentalUseCase;
import com.edu.tks.ports.view.service.rental.ArchiveRentalsUseCase;
import com.edu.tks.ports.view.service.rental.GetRentalsUseCase;
import com.edu.tks.service.rental.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentalViewAdapter implements AddRentalUseCase, ArchiveRentalsUseCase, GetRentalsUseCase {

    @Autowired
    private RentalService rentalService;

    @Override
    public RentalView createRental(String clientID, String recordID) throws InputExceptionView, NotFoundExceptionView {
        try {
            return RentalViewConverter.convertRentalToRentalView(rentalService.createRental(clientID, recordID));
        } catch (InputException e) {
            throw new InputExceptionView(e.getMessage());
        } catch (NotFoundException e) {
            throw new NotFoundExceptionView(e.getMessage());
        }
    }

    @Override
    public RentalView archiveRental(String rentalID) throws NotFoundExceptionView, InputExceptionView {
        try {
            return RentalViewConverter.convertRentalToRentalView(rentalService.archiveRental(rentalID));
        } catch (NotFoundException e) {
            throw new NotFoundExceptionView(e.getMessage());
        } catch (InputException e) {
            throw new InputExceptionView(e.getMessage());
        }
    }

    @Override
    public List<RentalView> getAllRentals() {
        return rentalService.getAllRentals()
                .stream()
                .map(RentalViewConverter::convertRentalToRentalView)
                .toList();
    }

    @Override
    public List<RentalView> getAllArchiveRentals() {
        return rentalService.getAllArchiveRentals()
                .stream()
                .map(RentalViewConverter::convertRentalToRentalView)
                .toList();
    }

    @Override
    public RentalView getRentalByID(String rentalID) throws NotFoundExceptionView {
        try {
            return RentalViewConverter.convertRentalToRentalView(rentalService.getRentalByID(rentalID));
        } catch (NotFoundException e) {
            throw new NotFoundExceptionView(e.getMessage());
        }
    }
}
