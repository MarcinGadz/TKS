package com.edu.tks.infrastructure.soap.record;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import model.record.RecordSOAPEntity;

public interface SOAPRentRecord {
    RecordSOAPEntity rent(String recordID) throws InputException, NotFoundException;
}
