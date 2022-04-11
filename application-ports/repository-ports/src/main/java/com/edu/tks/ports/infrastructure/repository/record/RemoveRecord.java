package com.edu.tks.ports.infrastructure.repository.record;

import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.RentalException;

public interface RemoveRecord {
    void removeRecord(String recordID) throws RentalException, NotFoundException;
}
