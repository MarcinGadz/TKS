package com.edu.tks.infrastructure.soap.record;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import model.record.RecordSOAPEntity;

public interface SOAPReturnRecord {
    RecordSOAPEntity returnRecord(String recordID) throws NotFoundException, InputException;
}
