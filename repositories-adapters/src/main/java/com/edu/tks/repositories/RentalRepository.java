package com.edu.tks.repositories;

import com.edu.tks.entity.RentalEntity;
import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RentalRepository {
    private List<RentalEntity> rentals = new ArrayList<>();
    private List<RentalEntity> archiveRentalEntitys = new ArrayList<>();

    public List<RentalEntity> getAllRentalEntitys() {
        return rentals;
    }

    public List<RentalEntity> getAllArchiveRentalEntitys() {
        return archiveRentalEntitys;
    }

    public RentalEntity getRentalEntityByID(String rentalID) throws NotFoundException {
        RentalEntity rental = rentals.stream()
                .filter( r -> rentalID.equals(r.getRentalID().toString()))
                .findFirst()
                .orElse(null);
        if (rental == null) {
            throw new NotFoundException("RecordEntity not found");
        }
        return rental;
    }

    public void appendRentalEntity(RentalEntity rental) {
        rentals.add(rental);
    }

    public void appendRentalEntitys(List<RentalEntity> rents) {
        rentals.addAll(rents);
    }

    public void archiveRentalEntity(String rentalID) throws NotFoundException, InputException {
        RentalEntity rental = this.getRentalEntityByID(rentalID);

        rental.returnRecord();
        archiveRentalEntitys.add(rental);
        rentals.remove(rental);
    }

    public void archiveRentalEntitys(List<RentalEntity> rents) throws InputException {
        for (RentalEntity rental : rentals) {
            rental.returnRecord();
        }
        rentals.removeAll(rents);
        archiveRentalEntitys.addAll(rents);
    }
}
