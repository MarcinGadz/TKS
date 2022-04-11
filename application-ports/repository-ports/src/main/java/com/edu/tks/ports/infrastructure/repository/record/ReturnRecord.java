package com.edu.tks.ports.infrastructure.repository.record;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.record.Record;

public interface ReturnRecord {
    Record returnRecord(String recordID) throws NotFoundException, InputException;
}
