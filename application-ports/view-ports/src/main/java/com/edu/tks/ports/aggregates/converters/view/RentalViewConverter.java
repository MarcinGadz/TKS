package com.edu.tks.ports.aggregates.converters.view;

import com.edu.tks.exception.InputException;
import com.edu.tks.model.RentalView;
import com.edu.tks.rental.Rental;

public class RentalViewConverter {
    public static Rental convertRentalViewToRental(RentalView rentalView) {
        try {
            return new Rental(
                    rentalView.getRentalID(),
                    rentalView.getClientID().toString(),
                    rentalView.getRecordID().toString(),
                    rentalView.getRentDate(),
                    rentalView.getExpectedReturnDate(),
                    rentalView.getActualReturnDate(),
                    rentalView.isActive()
            );
        } catch (InputException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't convert RentalView to Rental");
        }
    }

    public static RentalView convertRentalToRentalView(Rental rental) {
        try {
            return new RentalView(
                    rental.getRentalID(),
                    rental.getClientID(),
                    rental.getRecordID(),
                    rental.getRentDate(),
                    rental.getExpectedReturnDate(),
                    rental.getActualReturnDate(),
                    rental.isActive()
            );
        } catch (InputException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't convert RentalView to Rental");
        }
    }
}
