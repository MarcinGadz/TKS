package com.edu.tks.ports.infrastructure.repository.record;

import com.edu.tks.record.Record;

public interface AddRecord {
    Record appendRecord(Record record);

    Record updateRecord(String recordId, Record record);
}
