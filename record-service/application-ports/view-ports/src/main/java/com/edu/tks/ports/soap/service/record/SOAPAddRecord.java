package com.edu.tks.ports.soap.service.record;

import com.edu.tks.exception.SOAPNotFoundException;
import com.edu.tks.model.record.RecordSOAP;

import java.text.ParseException;

public interface SOAPAddRecord {
    RecordSOAP appendRecord(RecordSOAP record);
    RecordSOAP updateRecord(String recordId, RecordSOAP record) throws SOAPNotFoundException, ParseException;
}
