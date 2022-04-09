package com.edu.tks.infrastructure.soap.record;

import com.edu.tks.exception.NotFoundException;
import model.record.RecordSOAPEntity;

import java.text.ParseException;

public interface SOAPAddRecord {
    RecordSOAPEntity appendRecord(RecordSOAPEntity record);
    RecordSOAPEntity updateRecord(String recordId, RecordSOAPEntity record) throws NotFoundException, ParseException;
}
