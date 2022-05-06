package com.edu.tks.ports.view.service.record;

import com.edu.tks.exception.SOAPNotFoundException;
import com.edu.tks.exception.SOAPRentalException;
import com.edu.tks.rest.exception.NotFoundExceptionView;
import com.edu.tks.rest.exception.RentalExceptionView;

public interface RemoveRecordUseCase {
    void removeRecord(String recordID) throws RentalExceptionView, NotFoundExceptionView;
}
