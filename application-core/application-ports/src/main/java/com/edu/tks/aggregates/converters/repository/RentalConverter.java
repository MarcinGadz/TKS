package com.edu.tks.aggregates.converters.repository;

import com.edu.tks.entity.RentalEntity;
import com.edu.tks.exception.InputException;
import com.edu.tks.exception.PermissionException;
import com.edu.tks.rental.Rental;

import java.util.UUID;

public class RentalConverter {

    public static Rental convertRentalEntityToRental(RentalEntity rentalEntity) {
        try {
            return new Rental(
                    rentalEntity.getRentalID(),
                    rentalEntity.getClientID().toString(),
                    rentalEntity.getRecordID().toString(),
                    rentalEntity.getRentDate(),
                    rentalEntity.getExpectedReturnDate(),
                    rentalEntity.getActualReturnDate(),
                    rentalEntity.isActive()
            );
        } catch (InputException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't convert RentalEntity to Rental");
        }
    }

    public static RentalEntity convertRentalToRentalEntity(Rental rental) {
        try {
            return new RentalEntity(
                    rental.getRentalID(),
                    UUID.fromString(rental.getClientID()),
                    UUID.fromString(rental.getRecordID()),
                    rental.getRentDate(),
                    rental.getExpectedReturnDate(),
                    rental.getActualReturnDate(),
                    rental.isActive()
            );
        } catch (InputException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't convert RentalEntity to Rental");
        }
    }
}
