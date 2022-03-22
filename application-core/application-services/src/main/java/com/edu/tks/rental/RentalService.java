package com.edu.tks.rental;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.RentalException;
import com.edu.tks.infrastructure.repository.rental.AddRental;
import com.edu.tks.infrastructure.repository.rental.ArchiveRentals;
import com.edu.tks.infrastructure.repository.rental.GetRentals;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService<Repository extends AddRental & ArchiveRentals & GetRentals> {
    Repository repository;

    public Rental getRentalByID(String rentalID) throws NotFoundException {
        return repository.getRentalByID(rentalID);
    }

    public List<Rental> getAllRentals() {
        return repository.getAllRentals();
    }

    public List<Rental> getAllArchiveRentals() {
        return repository.getAllArchiveRentals();
    }

    public synchronized void appendRental(Rental rental){
        repository.appendRental(rental);
    }

    public synchronized void appendRentals(List<Rental> rents){
        repository.appendRentals(rents);
    }

    public synchronized void archiveRental(String rentalID) throws RentalException, NotFoundException, InputException {
        repository.archiveRental(rentalID);
    }

    public synchronized void archiveRentals(List<Rental> rents) throws RentalException, NotFoundException, InputException {
        repository.archiveRentals(rents);
    }

}
