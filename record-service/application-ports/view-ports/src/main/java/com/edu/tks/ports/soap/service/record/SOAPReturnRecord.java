package com.edu.tks.ports.soap.service.record;

import com.edu.tks.exception.SOAPInputException;
import com.edu.tks.exception.SOAPNotFoundException;
import com.edu.tks.model.record.RecordSOAP;

public interface SOAPReturnRecord {
    RecordSOAP returnRecord(String recordID) throws SOAPNotFoundException, SOAPInputException;
}
