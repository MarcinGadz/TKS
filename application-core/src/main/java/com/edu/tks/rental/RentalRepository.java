package com.edu.tks.rental;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class RentalRepository {
    private List<Rental> rentals = new ArrayList<>();
    private List<Rental> archiveRentals = new ArrayList<>();

    public List<Rental> getAllRentals() {
        return rentals;
    }

    public List<Rental> getAllArchiveRentals() {
        return archiveRentals;
    }

    public Rental getRentalByID(String rentalID) throws NotFoundException {
        Rental rental = rentals.stream()
                .filter( r -> rentalID.equals(r.getRentalID().toString()))
                .findFirst()
                .orElse(null);
        if (rental == null) {
            throw new NotFoundException("Record not found");
        }
        return rental;
    }

    public void appendRental(Rental rental) {
        rentals.add(rental);
    }

    public void appendRentals(List<Rental> rents) {
        rentals.addAll(rents);
    }

    public void archiveRental(String rentalID) throws NotFoundException, InputException {
        Rental rental = this.getRentalByID(rentalID);

        rental.returnRecord();
        archiveRentals.add(rental);
        rentals.remove(rental);
    }

    public void archiveRentals(List<Rental> rents) throws InputException {
        for (Rental rental : rentals) {
            rental.returnRecord();
        }
        rentals.removeAll(rents);
        archiveRentals.addAll(rents);
    }
}
