package com.edu.tks.rental;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.infrastructure.repository.record.GetRecords;
import com.edu.tks.infrastructure.repository.record.RentRecord;
import com.edu.tks.infrastructure.repository.rental.AddRental;
import com.edu.tks.infrastructure.repository.rental.ArchiveRentals;
import com.edu.tks.infrastructure.repository.rental.GetRentals;
import com.edu.tks.infrastructure.repository.user.GetUsers;
import com.edu.tks.record.Record;
import com.edu.tks.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService implements AddRentalUseCase, GetRentalsUseCase, ArchiveRentalsUseCase {

    private final GetRentals getRentals;
    private final AddRental addRental;
    private final ArchiveRentals archiveRentals;
    private final GetUsers getUsers;
    private final GetRecords getRecords;
    private final RentRecord rentRecord;

    @Autowired
    public RentalService(GetRentals getRentals, AddRental addRental, ArchiveRentals archiveRentals, GetUsers getUsers, GetRecords getRecords, RentRecord rentRecord) {
        this.getRentals = getRentals;
        this.addRental = addRental;
        this.archiveRentals = archiveRentals;
        this.getUsers = getUsers;
        this.getRecords = getRecords;
        this.rentRecord = rentRecord;
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
    public Rental createRental(String clientID, String recordID) throws InputException, NotFoundException {
        User user = getUsers.getUserByID(clientID);
        Record record = getRecords.getRecordByID(recordID);
        var newRental = new Rental(user, record);
        addRental.appendRental(newRental);
        rentRecord.rent(recordID);
        return newRental;
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
