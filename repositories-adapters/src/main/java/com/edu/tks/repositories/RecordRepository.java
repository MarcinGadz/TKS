package com.edu.tks.repositories;

import com.edu.tks.entity.RecordEntity;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.RentalException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class RecordRepository {
    private List<RecordEntity> records;

    public RecordRepository() {
        RecordEntity[] arr = {
          new RecordEntity("Moral Power", "Nothing but Lorde", "2020-03-04"),
          new RecordEntity("Solar Panic", "Thieves", "2021-10-13")
        };
        this.records  =  new ArrayList<>(Arrays.asList(arr));
    }

    public List<RecordEntity> getAllRecords() {
        return records;
    }

    public RecordEntity getRecordByID(String recordID) throws NotFoundException {
        RecordEntity record = records.stream()
                .filter( r -> recordID.equals(r.getRecordID().toString()))
                .findFirst()
                .orElse(null);
        if (record == null) {
            throw new NotFoundException("Record not found");
        }
        return record;
    }


    public void appendRecord(RecordEntity record) {
        records.add(record);
    }

    public void removeRecord(String recordID) throws RentalException, NotFoundException {
        RecordEntity record = this.getRecordByID(recordID);

        if (record.isRented()) {
            throw new RentalException("Can't remove - this record is rented.");
        }

        records.remove(record);
    }

    public RecordEntity updateRecord(String recordId, RecordEntity record) {
        RecordEntity tmp = null;
        try {
            tmp = this.getRecordByID(recordId);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        tmp.setReleaseDate(record.getReleaseDate());
        tmp.setArtist(record.getArtist());
        tmp.setTitle(record.getTitle());
        return tmp;
    }
}
