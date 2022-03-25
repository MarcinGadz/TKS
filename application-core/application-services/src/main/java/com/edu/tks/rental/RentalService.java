package com.edu.tks.rental;

import com.edu.tks.aggregates.adapters.RentalRepositoryAdapter;
import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.RentalException;
import com.edu.tks.infrastructure.repository.rental.AddRental;
import com.edu.tks.infrastructure.repository.rental.ArchiveRentals;
import com.edu.tks.infrastructure.repository.rental.GetRentals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {

    private final GetRentals getRentalsUseCase;
    private final AddRental addRentalUseCase;
    private final ArchiveRentals archiveRentalUseCase;

    @Autowired
    public RentalService(GetRentals getRentalsUseCase, AddRental addRentalUseCase, ArchiveRentals archiveRentalUseCase) {
        this.getRentalsUseCase = getRentalsUseCase;
        this.addRentalUseCase = addRentalUseCase;
        this.archiveRentalUseCase = archiveRentalUseCase;
    }

    public Rental getRentalByID(String rentalID) throws NotFoundException {
        return getRentalsUseCase.getRentalByID(rentalID);
    }

    public List<Rental> getAllRentals() {
        return getRentalsUseCase.getAllRentals();
    }

    public List<Rental> getAllArchiveRentals() {
        return getRentalsUseCase.getAllArchiveRentals();
    }

    public synchronized void appendRental(Rental rental) {
        addRentalUseCase.appendRental(rental);
    }

    public synchronized void appendRentals(List<Rental> rents) {
        addRentalUseCase.appendRentals(rents);
    }

    public synchronized void archiveRental(String rentalID) throws RentalException, NotFoundException, InputException {
        archiveRentalUseCase.archiveRental(rentalID);
    }

    public synchronized void archiveRentals(List<Rental> rents) throws RentalException, NotFoundException, InputException {
        archiveRentalUseCase.archiveRentals(rents);
    }

}
