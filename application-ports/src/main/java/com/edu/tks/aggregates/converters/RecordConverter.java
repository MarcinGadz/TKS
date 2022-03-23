package com.edu.tks.aggregates.converters;

import com.edu.tks.entity.RecordEntity;
import com.edu.tks.record.Record;

public class RecordConverter {

    public static Record convertRecordEntityToRecord(RecordEntity recordEntity) {
        return new Record(
                recordEntity.getTitle(),
                recordEntity.getArtist(),
                recordEntity.getReleaseDate().toString()
        );
    }

    public static RecordEntity convertRecordToRecordEntity(Record record) {
        return new RecordEntity(
                record.getTitle(),
                record.getArtist(),
                record.getReleaseDate().toString()
        );
    }
}
