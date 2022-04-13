package com.edu.tks.ports.view.service.record;

import com.edu.tks.exception.NotFoundExceptionView;
import com.edu.tks.exception.RentalExceptionView;

public interface RemoveRecordUseCase {
    void removeRecord(String recordID) throws RentalExceptionView, NotFoundExceptionView;
}
