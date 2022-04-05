package com.edu.tks.infrastructure.service.record;

import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.RentalException;

public interface RemoveRecordUseCase {
    void removeRecord(String recordID) throws RentalException, NotFoundException;
}
