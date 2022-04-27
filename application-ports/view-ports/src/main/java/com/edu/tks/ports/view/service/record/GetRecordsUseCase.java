package com.edu.tks.ports.view.service.record;

import com.edu.tks.exception.SOAPNotFoundException;
import com.edu.tks.rest.exception.NotFoundExceptionView;
import com.edu.tks.rest.model.RecordView;

import java.util.List;

public interface GetRecordsUseCase {
    List<RecordView> getAllRecords();
    RecordView getRecordByID(String recordID) throws NotFoundExceptionView;
}
