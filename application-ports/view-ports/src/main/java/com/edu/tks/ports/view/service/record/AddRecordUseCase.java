package com.edu.tks.ports.view.service.record;


import com.edu.tks.exception.SOAPNotFoundException;
import com.edu.tks.model.RecordView;

import java.text.ParseException;

public interface AddRecordUseCase {
    void appendRecord(RecordView record);

    RecordView updateRecord(String recordId, RecordView record) throws SOAPNotFoundException, ParseException;
}
