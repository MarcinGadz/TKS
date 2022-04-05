package com.edu.tks.infrastructure.repository.record;

import com.edu.tks.exception.NotFoundException;
import com.edu.tks.record.Record;

import java.util.List;

public interface GetRecords {
    List<Record> getAllRecords();
    Record getRecordByID(String recordID) throws NotFoundException;
}
