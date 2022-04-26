package com.edu.tks.soap;

import com.edu.tks.exception.*;
import com.edu.tks.model.record.*;
import com.edu.tks.model.user.GetUsersRequest;
import com.edu.tks.ports.soap.service.record.SOAPAddRecord;
import com.edu.tks.ports.soap.service.record.SOAPGetRecords;
import com.edu.tks.ports.soap.service.record.SOAPRemoveRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.text.ParseException;

@Endpoint
public class RecordSoapService {

    private static final String NAMESPACE_URI = "http://model.tks.edu.com/record";

    @Autowired
    private SOAPAddRecord soapAddRecord;
    @Autowired
    private SOAPGetRecords soapGetRecords;
    @Autowired
    private SOAPRemoveRecord soapRemoveRecord;

    public RecordSoapService() {}

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRecordsRequest")
    @ResponsePayload
    public GetRecordsResponse getRecords() {
        GetRecordsResponse response = new GetRecordsResponse();
        for (RecordSOAP record : soapGetRecords.getAllRecords()) {
            response.getRecord().add(record);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRecordByIdRequest")
    @ResponsePayload
    public GetRecordByIdResponse getRecordByID(@RequestPayload GetRecordByIdRequest request) throws SOAPNotFoundException {
        GetRecordByIdResponse response = new GetRecordByIdResponse();
        response.setRecord(soapGetRecords.getRecordByID(request.getId()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addRecordRequest")
    @ResponsePayload
    public AddRecordResponse addRecord(@RequestPayload AddRecordRequest request) throws InputExceptionView {
        RecordSOAP record = request.getRecord();

        String title = record.getTitle();
        if (!title.matches("^[a-zA-Z0-9_ -]{3,50}$")) {
            throw new InputExceptionView("Title must be between 3 and 50 characters");
        }

        String artist = record.getArtist();
        if (!artist.matches("^[a-zA-Z0-9_ -]{3,50}$")) {
            throw new InputExceptionView("Artist name must be between 3 and 50 characters");
        }

        AddRecordResponse response = new AddRecordResponse();
        response.setRecord(soapAddRecord.appendRecord(record));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "removeRecordRequest")
    @ResponsePayload
    public RemoveRecordResponse removeRecord(@RequestPayload RemoveRecordRequest request) throws SOAPNotFoundException, SOAPRentalException {
        RecordSOAP record = soapGetRecords.getRecordByID(request.getRecordID());
        soapRemoveRecord.removeRecord(request.getRecordID());

        RemoveRecordResponse response = new RemoveRecordResponse();
        response.setRecord(record);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "modifyRecordRequest")
    @ResponsePayload
    public ModifyRecordResponse modifyRecord(@RequestPayload ModifyRecordRequest request)
            throws SOAPInputException, SOAPNotFoundException {

        try {
            if (!request.getRecordID().matches("\\b[0-9a-f]{8}\\b-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-\\b[0-9a-f]{12}\\b")) {
                throw new SOAPInputException("Wrong uuid format");
            }

            String title = request.getRecord().getTitle();
            if (title.length() != 0 && !title.matches("^[a-zA-Z0-9_ -]{3,50}$")) {
                throw new SOAPInputException("Title must be between 3 and 50 characters");
            }


            String artist = request.getRecord().getArtist();
            if (artist.length() != 0 && !artist.matches("^[a-zA-Z0-9_ -]{3,50}$")) {
                throw new SOAPInputException("Artist name must be between 3 and 50 characters");
            }
            soapAddRecord.updateRecord(request.getRecordID(), request.getRecord());

            ModifyRecordResponse response = new ModifyRecordResponse();
            response.setRecord(request.getRecord());
            return response;

        } catch (ParseException e) {
            throw new SOAPInputException("Wrong date format");
        } catch (NullPointerException e) {
            throw new SOAPInputException("Not every field was provided");
        }
    }
}
