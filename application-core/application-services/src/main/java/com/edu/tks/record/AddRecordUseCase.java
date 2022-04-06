package com.edu.tks.record;


import com.edu.tks.exception.NotFoundException;

import java.text.ParseException;

public interface AddRecordUseCase {
    void appendRecord(Record record);

    Record updateRecord(String recordId, Record record) throws NotFoundException, ParseException;
}
