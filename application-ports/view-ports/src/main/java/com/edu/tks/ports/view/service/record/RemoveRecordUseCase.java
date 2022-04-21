package com.edu.tks.ports.view.service.record;

import com.edu.tks.exception.SOAPNotFoundException;
import com.edu.tks.exception.SOAPRentalException;

public interface RemoveRecordUseCase {
    void removeRecord(String recordID) throws SOAPRentalException, SOAPNotFoundException;
}
