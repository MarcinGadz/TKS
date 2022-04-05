package com.edu.tks.record;

import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.RentalException;
import com.edu.tks.infrastructure.repository.record.AddRecord;
import com.edu.tks.infrastructure.repository.record.GetRecords;
import com.edu.tks.infrastructure.repository.record.RemoveRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;


@Service
public class RecordService implements AddRecordUseCase, GetRecordsUseCase, RemoveRecordUseCase {
    private final AddRecord addRecord;
    private final GetRecords getRecords;
    private final RemoveRecord removeRecord;

    @Autowired
    public RecordService(AddRecord addRecord, GetRecords getRecords, RemoveRecord removeRecord) {
        this.addRecord = addRecord;
        this.getRecords = getRecords;
        this.removeRecord = removeRecord;
    }

    @Override
    public Record getRecordByID(String recordid) throws NotFoundException {
        return getRecords.getRecordByID(recordid);
    }

    @Override
    public List<Record> getAllRecords() {
        return getRecords.getAllRecords();
    }

    @Override
    public synchronized void appendRecord(Record record) {
        addRecord.appendRecord(record);
    }

    @Override
    public synchronized void removeRecord(String recordid) throws RentalException, NotFoundException {
        removeRecord.removeRecord(recordid);
    }

    // TODO clean it
    @Override
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
