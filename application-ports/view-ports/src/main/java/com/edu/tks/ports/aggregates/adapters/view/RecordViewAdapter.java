package com.edu.tks.ports.aggregates.adapters.view;

import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.RentalException;
import com.edu.tks.model.RecordView;
import com.edu.tks.ports.aggregates.converters.view.RecordViewConverter;
import com.edu.tks.ports.view.service.record.AddRecordUseCase;
import com.edu.tks.ports.view.service.record.GetRecordsUseCase;
import com.edu.tks.ports.view.service.record.RemoveRecordUseCase;
import com.edu.tks.service.record.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecordViewAdapter implements AddRecordUseCase, GetRecordsUseCase, RemoveRecordUseCase {
    @Autowired
    private RecordService recordService;

    @Override
    public List<RecordView> getAllRecords() {
        return recordService.getAllRecords()
                .stream()
                .map(RecordViewConverter::convertRecordToRecordView)
                .collect(Collectors.toList());
    }

    @Override
    public RecordView getRecordByID(String recordID) throws NotFoundException {
        return RecordViewConverter.convertRecordToRecordView(recordService.getRecordByID(recordID));
    }

    @Override
    public void removeRecord(String recordID) throws RentalException, NotFoundException {
        recordService.removeRecord(recordID);
    }

    @Override
    public void appendRecord(RecordView record) {
        recordService.appendRecord(RecordViewConverter.convertRecordViewToRecord(record));
    }

    @Override
    public RecordView updateRecord(String recordId, RecordView record) throws NotFoundException, ParseException {
        return RecordViewConverter.convertRecordToRecordView(recordService.updateRecord(recordId, RecordViewConverter.convertRecordViewToRecord(record)));
    }
}
