package com.edu.tks.infrastructure.soap.record;

import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.RentalException;
import model.record.RecordSOAPEntity;

public interface SOAPRemoveRecord {
    RecordSOAPEntity removeRecord(String recordID) throws RentalException, NotFoundException;
}
