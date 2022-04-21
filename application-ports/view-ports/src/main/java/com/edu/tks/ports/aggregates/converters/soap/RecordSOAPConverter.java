package com.edu.tks.ports.aggregates.converters.soap;

import com.edu.tks.model.record.RecordSOAP;
import com.edu.tks.ports.utils.DateConversion;
import com.edu.tks.record.Record;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.UUID;

public class RecordSOAPConverter {
    public static Record convertRecordSOAPEntityToRecord(RecordSOAP recordSOAP) {
        if(recordSOAP == null) return null;
        return new Record(
                UUID.fromString(recordSOAP.getRecordID()),
                recordSOAP.getTitle(),
                recordSOAP.getArtist(),
                recordSOAP.getReleaseDate().toString(),
                recordSOAP.isRented()
        );
    }

    public static RecordSOAP convertRecordToRecordSOAPEntity(Record record) {
        if(record == null) return null;
        RecordSOAP recordSOAP = new RecordSOAP();
        recordSOAP.setRecordID(record.getRecordID().toString());
        recordSOAP.setTitle(record.getTitle());
        recordSOAP.setArtist(record.getArtist());
        recordSOAP.setReleaseDate(DateConversion.convertToXMLGregorianCalendar(record.getReleaseDate()));
        return recordSOAP;
    }
}
