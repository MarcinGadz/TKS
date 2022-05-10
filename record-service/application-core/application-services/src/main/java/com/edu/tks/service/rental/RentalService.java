package com.edu.tks.service.rental;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.ports.infrastructure.repository.record.GetRecords;
import com.edu.tks.ports.infrastructure.repository.record.RentRecord;
import com.edu.tks.ports.infrastructure.repository.record.ReturnRecord;
import com.edu.tks.ports.infrastructure.repository.rental.AddRental;
import com.edu.tks.ports.infrastructure.repository.rental.ArchiveRentals;
import com.edu.tks.ports.infrastructure.repository.rental.GetRentals;
import com.edu.tks.record.Record;
import com.edu.tks.rental.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {

    private final GetRentals getRentals;
    private final AddRental addRental;
    private final ArchiveRentals archiveRentals;
    private final GetRecords getRecords;
    private final RentRecord rentRecord;
    private final ReturnRecord returnRecord;

    @Autowired
    public RentalService(GetRentals getRentals, AddRental addRental, ArchiveRentals archiveRentals, GetRecords getRecords, RentRecord rentRecord, ReturnRecord returnRecord) {
        this.getRentals = getRentals;
        this.addRental = addRental;
        this.archiveRentals = archiveRentals;
        this.getRecords = getRecords;
        this.rentRecord = rentRecord;
        this.returnRecord = returnRecord;
    }


    public Rental getRentalByID(String rentalID) throws NotFoundException {
        return getRentals.getRentalByID(rentalID);
    }


    public List<Rental> getAllRentals() {
        return getRentals.getAllRentals();
    }


    public List<Rental> getAllArchiveRentals() {
        return getRentals.getAllArchiveRentals();
    }


    public Rental createRental(String clientID, String recordID) throws InputException, NotFoundException {
        var newRental = new Rental(clientID, recordID);
        addRental.appendRental(newRental);
        rentRecord.rent(recordID);
        return newRental;
    }


    public synchronized Rental archiveRental(String rentalID) throws NotFoundException, InputException {
        Rental rental = getRentals.getRentalByID(rentalID);
        Record record = getRecords.getRecordByID(rental.getRecordID());
        returnRecord.returnRecord(record.getRecordID().toString());
        return archiveRentals.archiveRental(rentalID);
    }
}
