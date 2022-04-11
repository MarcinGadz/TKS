package com.edu.tks.ports.view.service.record;

import com.edu.tks.exception.NotFoundException;
import com.edu.tks.model.RecordView;

import java.util.List;

public interface GetRecordsUseCase {
    List<RecordView> getAllRecords();
    RecordView getRecordByID(String recordID) throws NotFoundException;
}
