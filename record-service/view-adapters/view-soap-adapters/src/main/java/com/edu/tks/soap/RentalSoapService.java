package com.edu.tks.soap;

import com.edu.tks.exception.SOAPInputException;
import com.edu.tks.exception.SOAPNotFoundException;
import com.edu.tks.model.rental.*;
import com.edu.tks.ports.soap.service.rental.SOAPAddRental;
import com.edu.tks.ports.soap.service.rental.SOAPArchiveRentals;
import com.edu.tks.ports.soap.service.rental.SOAPGetRentals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class RentalSoapService {

    private static final String NAMESPACE_URI = "http://model.tks.edu.com/rental";

    @Autowired
    private SOAPAddRental soapAddRental;
    @Autowired
    private SOAPGetRentals soapGetRentals;
    @Autowired
    private SOAPArchiveRentals soapArchiveRentals;

    public RentalSoapService() {}

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllRentalsRequest")
    @ResponsePayload
    public GetAllRentalsResponse getAllRentals() throws SOAPInputException {
        GetAllRentalsResponse response = new GetAllRentalsResponse();
        for (RentalSOAP rental : soapGetRentals.getAllRentals()) {
            response.getRental().add(rental);
        }
        return response;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllArchiveRentalsRequest")
    @ResponsePayload
    public GetAllArchiveRentalsResponse getAllArchiveRentals() {
        GetAllArchiveRentalsResponse response = new GetAllArchiveRentalsResponse();
        for (RentalSOAP rental : soapGetRentals.getAllArchiveRentals()) {
            response.getRental().add(rental);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "rentRecordRequest")
    @ResponsePayload
    public RentRecordResponse rentRecord(@RequestPayload RentRecordRequest request) throws SOAPNotFoundException, SOAPInputException {
        RentRecordResponse response = new RentRecordResponse();
        response.setRental(soapAddRental.createRental(request.getUserID(), request.getRecordID()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "returnRecordRequest")
    @ResponsePayload
    public ReturnRecordResponse returnRecord(@RequestPayload ReturnRecordRequest request) throws SOAPNotFoundException, SOAPInputException {
        RentalSOAP rental = soapGetRentals.getRentalByID(request.getRentalID());

        System.out.println("Request clientID: " + request.getUserID());
        System.out.println("Actual clientID: " + rental.getClientID());
        if (!rental.getClientID().equals(request.getUserID())) {
            System.out.println("THIS USER DOES NOT OWN THIS RECORD");
            throw new SOAPInputException("This user does not own this record!");
        }

        ReturnRecordResponse response = new ReturnRecordResponse();

        response.setRental(soapArchiveRentals.archiveRental(request.getRentalID()));
        return response;
    }
}
