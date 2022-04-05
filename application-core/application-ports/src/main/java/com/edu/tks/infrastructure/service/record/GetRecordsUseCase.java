package com.edu.tks.infrastructure.service.record;

import com.edu.tks.exception.NotFoundException;
import com.edu.tks.record.Record;

import java.util.List;

public interface GetRecordsUseCase {
    List<Record> getAllRecords();
    Record getRecordByID(String recordID) throws NotFoundException;
}
