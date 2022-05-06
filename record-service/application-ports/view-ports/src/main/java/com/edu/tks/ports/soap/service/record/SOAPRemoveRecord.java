package com.edu.tks.ports.soap.service.record;

import com.edu.tks.exception.SOAPNotFoundException;
import com.edu.tks.exception.SOAPRentalException;
import com.edu.tks.model.record.RecordSOAP;

public interface SOAPRemoveRecord {
    RecordSOAP removeRecord(String recordID) throws SOAPRentalException, SOAPNotFoundException;
}
