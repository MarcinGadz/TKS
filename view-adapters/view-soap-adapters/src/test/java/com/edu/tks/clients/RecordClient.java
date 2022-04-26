package com.edu.tks.clients;

import com.edu.tks.model.record.*;
import com.edu.tks.model.user.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class RecordClient extends WebServiceGatewaySupport {

    private final String location;

    public RecordClient(int port) {
        location = "http://localhost:" + port + "/ws";
    }

    public GetRecordsResponse getRecords() {
        GetRecordsRequest request = new GetRecordsRequest();
        return (GetRecordsResponse) getWebServiceTemplate().marshalSendAndReceive(
                location,
                request,
                new SoapActionCallback(
                        "http://model.tks.edu.com/record/GetRecordsRequest")
        );
    }

    public GetRecordByIdResponse getRecordByID(String recordID) {

        GetRecordByIdRequest request = new GetRecordByIdRequest();
        request.setId(recordID);

        return (GetRecordByIdResponse) getWebServiceTemplate().marshalSendAndReceive(
                location,
                request,
                new SoapActionCallback(
                        "http://model.tks.edu.com/record/GetRecordByIdRequest")
        );
    }

    public AddRecordResponse addRecord(RecordSOAP user) {

        AddRecordRequest request = new AddRecordRequest();
        request.setRecord(user);

        return (AddRecordResponse) getWebServiceTemplate().marshalSendAndReceive(
                location,
                request,
                new SoapActionCallback(
                        "http://model.tks.edu.com/record/AddRecordRequest")
        );
    }

    public RemoveRecordResponse removeRecord(String recordID) {

        RemoveRecordRequest request = new RemoveRecordRequest();
        request.setRecordID(recordID);

        return (RemoveRecordResponse) getWebServiceTemplate().marshalSendAndReceive(
                location,
                request,
                new SoapActionCallback(
                        "http://model.tks.edu.com/record/RemoveRecordRequest")
        );
    }

    public ModifyRecordResponse activateRecord(String recordID, RecordSOAP record) {

        ModifyRecordRequest request = new ModifyRecordRequest();
        request.setRecordID(recordID);
        request.setRecord(record);

        return (ModifyRecordResponse) getWebServiceTemplate().marshalSendAndReceive(
                location,
                request,
                new SoapActionCallback(
                        "http://model.tks.edu.com/record/ModifyRecordRequest")
        );
    }
}
