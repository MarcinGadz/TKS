package com.edu.tks;

import com.edu.tks.clients.RecordClient;
import com.edu.tks.model.record.RecordSOAP;
import com.edu.tks.soap.SoapServiceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;


import javax.annotation.PostConstruct;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;

import static com.edu.tks.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SoapServiceApplication.class)
class RecordSoapServiceTest {

    private static List<RecordSOAP> testData;

    private RecordClient recordClient;

    @LocalServerPort
    private int port;

    @PostConstruct
    private void init() throws DatatypeConfigurationException {
        String BASE_PATH = "http://localhost:" + port;

        Jaxb2Marshaller marshaller = getRecordMarshaller();
        recordClient = new RecordClient(port);
        recordClient.setDefaultUri(BASE_PATH + "/ws");
        recordClient.setMarshaller(marshaller);
        recordClient.setUnmarshaller(marshaller);

        testData = new ArrayList<>();
        RecordSOAP record;
        XMLGregorianCalendar releaseDate;
        record = new RecordSOAP();
        record.setRecordID("02cf35bf-d025-440b-a6ec-17cc6c77b021");
        record.setTitle("Moral Power");
        record.setArtist("Nothing but Lorde");
        releaseDate = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
                2020, 3, 4, DatatypeConstants.FIELD_UNDEFINED);
        record.setReleaseDate(releaseDate);
        record.setRented(true);
        testData.add(record);
        record = new RecordSOAP();
        record.setRecordID("3e3719e5-8689-4e65-883f-4cd06cae7195");
        record.setTitle("Solar Panic");
        record.setArtist("Thieves");
        releaseDate = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
                2021, 10, 13, DatatypeConstants.FIELD_UNDEFINED);
        record.setReleaseDate(releaseDate);
        record.setRented(false);
        testData.add(record);
    }

    private void compareRecords(RecordSOAP r1, RecordSOAP r2, boolean compareIDs) {
        if (compareIDs) assertEquals(r1.getRecordID(), r2.getRecordID());
        assertEquals(r1.getTitle(), r2.getTitle());
        assertEquals(r1.getArtist(), r2.getArtist());
        assertEquals(r1.getReleaseDate(), r2.getReleaseDate());
        assertEquals(r1.isRented(), r2.isRented());
    }

    @Test
    public void getRecordById() {
        RecordSOAP record = recordClient.getRecordByID(testData.get(0).getRecordID()).getRecord();
        compareRecords(record, testData.get(0), true);
    }

    @Test
    void getRecordsTests() {
        List<RecordSOAP> records = recordClient.getRecords().getRecord();
        for (int i = 0; i < testData.size(); i++) {
            compareRecords(records.get(i), testData.get(i), true);
        }
    }

    @Test
    void addAndRemoveRecord() throws DatatypeConfigurationException {
        RecordSOAP newRecord = new RecordSOAP();
        newRecord.setRecordID("a3922462-af43-493d-b775-274885349f5e");
        newRecord.setTitle("TestRecordTitle0");
        newRecord.setArtist("TestRecordArtist0");
        XMLGregorianCalendar releaseDate = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
                2022, 7, 26, DatatypeConstants.FIELD_UNDEFINED);
        newRecord.setReleaseDate(releaseDate);
        newRecord.setRented(false);

        RecordSOAP addedRecord = recordClient.addRecord(newRecord).getRecord();
        compareRecords(addedRecord, newRecord, false);

        RecordSOAP removedRecord = recordClient.removeRecord(addedRecord.getRecordID()).getRecord();
        compareRecords(removedRecord, addedRecord, true);
    }
}