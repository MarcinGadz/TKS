package com.edu.tks.rental;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.infrastructure.repository.rental.AddRental;
import com.edu.tks.infrastructure.repository.rental.ArchiveRentals;
import com.edu.tks.infrastructure.repository.rental.GetRentals;
import com.edu.tks.infrastructure.service.rental.AddRentalUseCase;
import com.edu.tks.infrastructure.service.rental.ArchiveRentalsUseCase;
import com.edu.tks.infrastructure.service.rental.GetRentalsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService implements AddRentalUseCase, GetRentalsUseCase, ArchiveRentalsUseCase {

    private final GetRentals getRentals;
    private final AddRental addRental;
    private final ArchiveRentals archiveRentals;

    @Autowired
    public RentalService(GetRentals getRentals, AddRental addRental, ArchiveRentals archiveRentals) {
        this.getRentals = getRentals;
        this.addRental = addRental;
        this.archiveRentals = archiveRentals;
    }

    @Override
    public Rental getRentalByID(String rentalID) throws NotFoundException {
        return getRentals.getRentalByID(rentalID);
    }

    @Override
    public List<Rental> getAllRentals() {
        return getRentals.getAllRentals();
    }

    @Override
    public List<Rental> getAllArchiveRentals() {
        return getRentals.getAllArchiveRentals();
    }

    @Override
    public synchronized void appendRental(Rental rental) {
        addRental.appendRental(rental);
    }

    @Override
    public synchronized void appendRentals(List<Rental> rents) {
        addRental.appendRentals(rents);
    }

    @Override
    public synchronized void archiveRental(String rentalID) throws NotFoundException, InputException {
        archiveRentals.archiveRental(rentalID);
    }

    @Override
    public synchronized void archiveRentals(List<Rental> rents) throws InputException {
        archiveRentals.archiveRentals(rents);
    }

}
