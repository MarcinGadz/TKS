package com.edu.tks.clients;

import com.edu.tks.model.rental.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class RentalClient extends WebServiceGatewaySupport {

    private final String location;

    public RentalClient(int port) {
        location = "http://localhost:" + port + "/ws";
    }

    public GetAllRentalsResponse getAllRentals() {

        GetAllRentalsRequest request = new GetAllRentalsRequest();

        return (GetAllRentalsResponse) getWebServiceTemplate().marshalSendAndReceive(
                location,
                request,
                new SoapActionCallback(
                        "http://model.tks.edu.com/record/GetAllRentalsRequest")
        );
    }

    public GetAllArchiveRentalsResponse getAllArchiveRentals() {

        GetAllArchiveRentalsRequest request = new GetAllArchiveRentalsRequest();

        return (GetAllArchiveRentalsResponse) getWebServiceTemplate().marshalSendAndReceive(
                location,
                request,
                new SoapActionCallback(
                        "http://model.tks.edu.com/record/GetAllArchiveRentalsRequest")
        );
    }

    public RentRecordResponse rentRecord(String userID, String recordID) {

        RentRecordRequest request = new RentRecordRequest();
        request.setUserID(userID);
        request.setRecordID(recordID);

        return (RentRecordResponse) getWebServiceTemplate().marshalSendAndReceive(
                location,
                request,
                new SoapActionCallback(
                        "http://model.tks.edu.com/record/RentRecordRequest")
        );
    }

    public ReturnRecordResponse returnRecord(String userID, String rentalID) {

        ReturnRecordRequest request = new ReturnRecordRequest();
        request.setRentalID(rentalID);
        request.setUserID(userID);

        return (ReturnRecordResponse) getWebServiceTemplate().marshalSendAndReceive(
                location,
                request,
                new SoapActionCallback(
                        "http://model.tks.edu.com/record/ReturnRecordRequest")
        );
    }
}
