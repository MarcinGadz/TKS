package com.edu.tks.aggregates.adapters.soap;

import com.edu.tks.aggregates.converters.soap.RecordSOAPConverter;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.RentalException;
import com.edu.tks.infrastructure.soap.record.*;
import com.edu.tks.record.AddRecordUseCase;
import com.edu.tks.record.GetRecordsUseCase;
import com.edu.tks.record.RemoveRecordUseCase;
import model.record.RecordSOAPEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

public class RecordSOAPAdapter implements SOAPAddRecord, SOAPGetRecords, SOAPRemoveRecord {

    @Autowired
    private AddRecordUseCase addRecordUseCase;
    @Autowired
    private RemoveRecordUseCase removeRecordUseCase;
    @Autowired
    private GetRecordsUseCase getRecordsUseCase;

    @Override
    public RecordSOAPEntity appendRecord(RecordSOAPEntity record) {
        return RecordSOAPConverter.convertRecordToRecordSOAPEntity(addRecordUseCase.appendRecord(RecordSOAPConverter.convertRecordSOAPEntityToRecord(record)));
    }

    @Override
    public RecordSOAPEntity updateRecord(String recordId, RecordSOAPEntity record) throws NotFoundException, ParseException {
        return RecordSOAPConverter.convertRecordToRecordSOAPEntity(addRecordUseCase.updateRecord(recordId, RecordSOAPConverter.convertRecordSOAPEntityToRecord(record)));
    }

    @Override
    public List<RecordSOAPEntity> getAllRecords() {
        return getRecordsUseCase.getAllRecords().stream()
                .map(RecordSOAPConverter::convertRecordToRecordSOAPEntity)
                .collect(Collectors.toList());
    }

    @Override
    public RecordSOAPEntity getRecordByID(String recordID) throws NotFoundException {
        return RecordSOAPConverter.convertRecordToRecordSOAPEntity(getRecordsUseCase.getRecordByID(recordID));
    }

    @Override
    public RecordSOAPEntity removeRecord(String recordID) throws RentalException, NotFoundException {
        return RecordSOAPConverter.convertRecordToRecordSOAPEntity(removeRecordUseCase.removeRecord(recordID));
    }

}
