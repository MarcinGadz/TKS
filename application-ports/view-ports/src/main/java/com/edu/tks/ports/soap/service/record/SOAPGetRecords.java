package com.edu.tks.ports.soap.service.record;

import com.edu.tks.exception.SOAPNotFoundException;
import com.edu.tks.model.record.RecordSOAP;

import java.util.List;

public interface SOAPGetRecords {
    List<RecordSOAP> getAllRecords();
    RecordSOAP getRecordByID(String recordID) throws SOAPNotFoundException;
}
