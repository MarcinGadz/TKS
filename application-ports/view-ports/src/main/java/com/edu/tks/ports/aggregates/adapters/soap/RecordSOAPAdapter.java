package com.edu.tks.ports.aggregates.adapters.soap;

import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.RentalException;
import com.edu.tks.exception.SOAPNotFoundException;
import com.edu.tks.exception.SOAPRentalException;
import com.edu.tks.model.record.RecordSOAP;
import com.edu.tks.ports.aggregates.converters.soap.RecordSOAPConverter;
import com.edu.tks.ports.soap.service.record.SOAPAddRecord;
import com.edu.tks.ports.soap.service.record.SOAPGetRecords;
import com.edu.tks.ports.soap.service.record.SOAPRemoveRecord;
import com.edu.tks.service.record.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecordSOAPAdapter implements SOAPAddRecord, SOAPGetRecords, SOAPRemoveRecord {

    @Autowired
    private RecordService recordService;

    @Override
    public RecordSOAP appendRecord(RecordSOAP record) {
        return RecordSOAPConverter.convertRecordToRecordSOAPEntity(recordService.appendRecord(RecordSOAPConverter.convertRecordSOAPEntityToRecord(record)));
    }

    @Override
    public RecordSOAP updateRecord(String recordId, RecordSOAP record) throws ParseException, SOAPNotFoundException {
        try {
            return RecordSOAPConverter.convertRecordToRecordSOAPEntity(recordService.updateRecord(recordId, RecordSOAPConverter.convertRecordSOAPEntityToRecord(record)));
        } catch (NotFoundException e) {
            throw new SOAPNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<RecordSOAP> getAllRecords() {
        return recordService.getAllRecords().stream()
                .map(RecordSOAPConverter::convertRecordToRecordSOAPEntity)
                .collect(Collectors.toList());
    }

    @Override
    public RecordSOAP getRecordByID(String recordID) throws SOAPNotFoundException {
        try {
            return RecordSOAPConverter.convertRecordToRecordSOAPEntity(recordService.getRecordByID(recordID));
        } catch (NotFoundException e) {
            throw new SOAPNotFoundException(e.getMessage());
        }
    }

    @Override
    public RecordSOAP removeRecord(String recordID) throws SOAPRentalException, SOAPNotFoundException {
        try {
            return RecordSOAPConverter.convertRecordToRecordSOAPEntity(recordService.removeRecord(recordID));
        } catch (RentalException e) {
            throw new SOAPRentalException(e.getMessage());
        } catch (NotFoundException e) {
            throw new SOAPNotFoundException(e.getMessage());
        }
    }

}
