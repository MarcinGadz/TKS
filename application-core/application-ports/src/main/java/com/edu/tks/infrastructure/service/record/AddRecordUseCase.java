package com.edu.tks.infrastructure.service.record;


// TODO REMOVE PACKAGE IF NOT NEEDED
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.record.Record;

import java.text.ParseException;

public interface AddRecordUseCase {
    void appendRecord(Record record);

    Record updateRecord(String recordId, Record record) throws NotFoundException, ParseException;
}
