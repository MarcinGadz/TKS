package com.edu.tks.aggregates.converters.soap;

import com.edu.tks.entity.RecordEntity;
import com.edu.tks.record.Record;
import model.record.RecordSOAPEntity;

public class RecordSOAPConverter {
    public static Record convertRecordSOAPEntityToRecord(RecordSOAPEntity recordSOAP) {
        if(recordSOAP == null) return null;
        return new Record(
                recordSOAP.getRecordID(),
                recordSOAP.getTitle(),
                recordSOAP.getArtist(),
                recordSOAP.getReleaseDate().toString(),
                recordSOAP.isRented()
        );
    }

    public static RecordSOAPEntity convertRecordToRecordSOAPEntity(Record record) {
        if(record == null) return null;
        return new RecordSOAPEntity(
                record.getRecordID(),
                record.getTitle(),
                record.getArtist(),
                record.getReleaseDate().toString(),
                record.isRented()
        );
    }
}
