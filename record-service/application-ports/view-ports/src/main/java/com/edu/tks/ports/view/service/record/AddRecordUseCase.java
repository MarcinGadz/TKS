package com.edu.tks.ports.view.service.record;

import com.edu.tks.rest.exception.NotFoundExceptionView;
import com.edu.tks.rest.model.RecordView;

import java.text.ParseException;

public interface AddRecordUseCase {
    void appendRecord(RecordView record);

    RecordView updateRecord(String recordId, RecordView record) throws NotFoundExceptionView, ParseException;
}
