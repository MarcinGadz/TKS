package com.edu.tks.infrastructure.soap.record;

import model.record.RecordSOAPEntity;

public interface SOAPAddRecord {
    RecordSOAPEntity appendRecord(RecordSOAPEntity record);
    RecordSOAPEntity updateRecord(String recordId, RecordSOAPEntity record);
}
