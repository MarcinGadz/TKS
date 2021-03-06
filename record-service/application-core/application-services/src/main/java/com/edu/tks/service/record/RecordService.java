package com.edu.tks.service.record;

import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.RentalException;
import com.edu.tks.ports.infrastructure.repository.record.AddRecord;
import com.edu.tks.ports.infrastructure.repository.record.GetRecords;
import com.edu.tks.ports.infrastructure.repository.record.RemoveRecord;
import com.edu.tks.ports.infrastructure.repository.record.RentRecord;
import com.edu.tks.record.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;


@Service
public class RecordService {
    private final AddRecord addRecord;
    private final GetRecords getRecords;
    private final RemoveRecord removeRecord;

    @Autowired
    public RecordService(AddRecord addRecord, GetRecords getRecords, RemoveRecord removeRecord) {
        this.addRecord = addRecord;
        this.getRecords = getRecords;
        this.removeRecord = removeRecord;
    }


    public Record getRecordByID(String recordID) throws NotFoundException {
        return getRecords.getRecordByID(recordID);
    }


    public List<Record> getAllRecords() {
        return getRecords.getAllRecords();
    }


    public synchronized Record appendRecord(Record record) {
        record.setRecordID(UUID.randomUUID().toString());
        return addRecord.appendRecord(record);
    }


    public synchronized Record removeRecord(String recordid) throws RentalException, NotFoundException {
        return removeRecord.removeRecord(recordid);
    }


    public Record updateRecord(String recordId, Record record) throws NotFoundException, ParseException {
        return modifyRecord(recordId, record.getTitle(), record.getArtist(), record.getReleaseDate().toString());
    }

    public synchronized Record modifyRecord(String recordId, String title, String artist, String releaseDate) throws ParseException, NotFoundException {
        Record record = getRecords.getRecordByID(recordId);
        if (title.length() > 0) {
            record.setTitle(title);
        }

        if (artist.length() > 0) {
            record.setArtist(artist);
        }

        if (releaseDate != null && releaseDate.length() > 0) {
            record.setReleaseDate(releaseDate);
        }
        record = addRecord.updateRecord(recordId, record);
        return record;
    }
}
