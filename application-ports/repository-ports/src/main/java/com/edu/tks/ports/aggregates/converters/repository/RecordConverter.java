package com.edu.tks.ports.aggregates.converters.repository;

import com.edu.tks.repo.entity.RecordEntity;
import com.edu.tks.record.Record;

import java.util.UUID;

public class RecordConverter {

    public static Record convertRecordEntityToRecord(RecordEntity recordEntity) {
        if(recordEntity == null) return null;
        return new Record(
                recordEntity.getRecordID(),
                recordEntity.getTitle(),
                recordEntity.getArtist(),
                recordEntity.getReleaseDate().toString(),
                recordEntity.isRented()
        );
    }

    public static RecordEntity convertRecordToRecordEntity(Record record) {
        if(record == null) return null;
        return new RecordEntity(
                record.getRecordID(),
                record.getTitle(),
                record.getArtist(),
                record.getReleaseDate().toString(),
                record.isRented()
        );
    }
}
