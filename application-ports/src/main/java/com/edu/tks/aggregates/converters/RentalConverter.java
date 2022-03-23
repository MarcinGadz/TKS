package com.edu.tks.aggregates.converters;

import com.edu.tks.entity.RentalEntity;
import com.edu.tks.exception.InputException;
import com.edu.tks.exception.PermissionException;
import com.edu.tks.rental.Rental;

public class RentalConverter {

    public static Rental convertRentalEntityToRental(RentalEntity rentalEntity) {
        try {
            return new Rental(
                    rentalEntity.getRentalID(),
                    UserConverter.convertUserEntityToUser(rentalEntity.getClient()),
                    UserConverter.convertUserEntityToUser(rentalEntity.getRenter()),
                    RecordConverter.convertRecordEntityToRecord(rentalEntity.getRecord())
            );
        } catch (PermissionException | InputException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't convert RentalEntity to Rental");
        }
    }

    public static RentalEntity convertRentalToRentalEntity(Rental rental) {
        try {
            return new RentalEntity(
                    rental.getRentalID(),
                    UserConverter.convertUserToUserEntity(rental.getClient()),
                    UserConverter.convertUserToUserEntity(rental.getRenter()),
                    RecordConverter.convertRecordToRecordEntity(rental.getRecord())
            );
        } catch (PermissionException | InputException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't convert RentalEntity to Rental");
        }
    }
}
