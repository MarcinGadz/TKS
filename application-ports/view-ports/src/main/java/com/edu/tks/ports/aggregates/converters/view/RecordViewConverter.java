package com.edu.tks.ports.aggregates.converters.view;

import com.edu.tks.model.RecordView;
import com.edu.tks.record.Record;

public class RecordViewConverter {
    public static Record convertRecordViewToRecord(RecordView recordView) {
        if(recordView == null) return null;
        return new Record(
                recordView.getRecordID(),
                recordView.getTitle(),
                recordView.getArtist(),
                recordView.getReleaseDate().toString(),
                recordView.isRented()
        );
    }

    public static RecordView convertRecordToRecordView(Record record) {
        if(record == null) return null;
        return new RecordView(
                record.getRecordID(),
                record.getTitle(),
                record.getArtist(),
                record.getReleaseDate().toString(),
                record.isRented()
        );
    }
}
