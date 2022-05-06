package com.edu.tks.ports.infrastructure.repository.record;

import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.RentalException;
import com.edu.tks.record.Record;

public interface RemoveRecord {
    Record removeRecord(String recordID) throws RentalException, NotFoundException;
}
