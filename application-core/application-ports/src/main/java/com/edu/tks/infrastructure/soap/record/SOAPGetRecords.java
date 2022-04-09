package com.edu.tks.infrastructure.soap.record;

import com.edu.tks.exception.NotFoundException;
import model.record.RecordSOAPEntity;

import java.util.List;

public interface SOAPGetRecords {
    List<RecordSOAPEntity> getAllRecords();
    RecordSOAPEntity getRecordByID(String recordID) throws NotFoundException;
}
