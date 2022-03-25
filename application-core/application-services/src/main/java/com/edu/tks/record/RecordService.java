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
public class RecordService {
    private final AddRecord addRecordUseCase;
    private final GetRecords getRecordUseCase;
    private final RemoveRecord removeRecordUseCase;

    @Autowired
    public RecordService(AddRecord addRecordUseCase, GetRecords getRecordUseCase, RemoveRecord removeRecordUseCase) {
        this.addRecordUseCase = addRecordUseCase;
        this.getRecordUseCase = getRecordUseCase;
        this.removeRecordUseCase = removeRecordUseCase;
    }

    public Record getRecordByID(String recordid) throws NotFoundException {
        return getRecordUseCase.getRecordByID(recordid);
    }

    public List<Record> getAllRecords() {
        return getRecordUseCase.getAllRecords();
    }

    public synchronized void appendRecord(Record record) {
        addRecordUseCase.appendRecord(record);
    }

    public synchronized void removeRecord(String recordid) throws RentalException, NotFoundException {
        removeRecordUseCase.removeRecord(recordid);
    }

    public synchronized Record modifyRecord(String recordId, String title, String artist, String releaseDate) throws ParseException, NotFoundException {
        Record record = getRecordUseCase.getRecordByID(recordId);
        if (title.length() > 0) {
            record.setTitle(title);
        }

        if (artist.length() > 0) {
            record.setArtist(artist);
        }

        if (releaseDate != null && releaseDate.length() > 0) {
            record.setReleaseDate(releaseDate);
        }
        record = addRecordUseCase.updateRecord(recordId, record);
        return record;
    }

}
