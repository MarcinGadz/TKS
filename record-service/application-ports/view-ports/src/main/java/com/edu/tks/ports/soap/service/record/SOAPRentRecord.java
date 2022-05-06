package com.edu.tks.ports.soap.service.record;

import com.edu.tks.exception.SOAPInputException;
import com.edu.tks.exception.SOAPNotFoundException;
import com.edu.tks.model.record.RecordSOAP;

public interface SOAPRentRecord {
    RecordSOAP rent(String recordID) throws SOAPInputException, SOAPNotFoundException;
}
