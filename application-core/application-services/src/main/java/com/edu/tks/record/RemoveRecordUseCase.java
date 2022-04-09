package com.edu.tks.record;

import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.RentalException;

public interface RemoveRecordUseCase {
    Record removeRecord(String recordID) throws RentalException, NotFoundException;
}
