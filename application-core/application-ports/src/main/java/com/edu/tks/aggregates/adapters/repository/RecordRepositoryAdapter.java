package com.edu.tks.aggregates.adapters.repository;

import com.edu.tks.aggregates.converters.repository.RecordConverter;
import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.RentalException;
import com.edu.tks.infrastructure.repository.record.AddRecord;
import com.edu.tks.infrastructure.repository.record.GetRecords;
import com.edu.tks.infrastructure.repository.record.RemoveRecord;
import com.edu.tks.infrastructure.repository.record.RentRecord;
import com.edu.tks.record.Record;
import com.edu.tks.repositories.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecordRepositoryAdapter implements AddRecord, GetRecords, RemoveRecord, RentRecord {

    @Override
    public Record updateRecord(String recordId, Record record) {
        return RecordConverter.convertRecordEntityToRecord(repo.updateRecord(recordId, RecordConverter.convertRecordToRecordEntity(record)));
    }

    @Autowired
    private RecordRepository repo;

    @Override
    public void appendRecord(Record record) {
        repo.appendRecord(RecordConverter.convertRecordToRecordEntity(record));
    }

    @Override
    public List<Record> getAllRecords() {
        return repo.getAllRecords().stream()
                .map(RecordConverter::convertRecordEntityToRecord)
                .collect(Collectors.toList());
    }

    @Override
    public Record getRecordByID(String recordID) throws NotFoundException {
        return RecordConverter.convertRecordEntityToRecord(repo.getRecordByID(recordID));
    }

    @Override
    public void removeRecord(String recordID) throws RentalException, NotFoundException {
        repo.removeRecord(recordID);
    }

    @Override
    public void rent(String recordID) throws InputException, NotFoundException {
        repo.getRecordByID(recordID).rent();
    }
}
