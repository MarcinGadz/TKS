package com.edu.tks.ports.aggregates.adapters.repository;

import com.edu.tks.ports.aggregates.converters.repository.RecordConverter;
import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.RentalException;
import com.edu.tks.ports.infrastructure.repository.record.*;
import com.edu.tks.record.Record;
import com.edu.tks.repo.entity.RecordEntity;
import com.edu.tks.repo.repositories.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecordRepositoryAdapter implements AddRecord, GetRecords, RemoveRecord, RentRecord, ReturnRecord {

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
                .toList();
    }

    @Override
    public Record getRecordByID(String recordID) throws NotFoundException {
        try {
            return RecordConverter.convertRecordEntityToRecord(repo.getRecordByID(recordID));
        } catch (com.edu.tks.repo.exception.NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public void removeRecord(String recordID) throws RentalException, NotFoundException {
        try {
            repo.removeRecord(recordID);
        } catch (com.edu.tks.repo.exception.RentalException e) {
            throw new RentalException(e.getMessage());
        } catch (com.edu.tks.repo.exception.NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public void rent(String recordID) throws InputException, NotFoundException {
        try {
            repo.getRecordByID(recordID).rent();
        } catch (com.edu.tks.repo.exception.InputException e) {
            throw new InputException(e.getMessage());
        } catch (com.edu.tks.repo.exception.NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public Record returnRecord(String recordID) throws NotFoundException, InputException {
        RecordEntity recordEntity = null;
        try {
            recordEntity = repo.getRecordByID(recordID);
        } catch (com.edu.tks.repo.exception.NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        try {
            recordEntity.release();
        } catch (com.edu.tks.repo.exception.InputException e) {
            throw new InputException(e.getMessage());
        }
        return RecordConverter.convertRecordEntityToRecord(recordEntity);
    }

}
