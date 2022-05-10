package com.edu.tks;

import com.edu.tks.clients.RentalClient;
import com.edu.tks.model.rental.RentalSOAP;
import com.edu.tks.soap.SOAPApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.annotation.PostConstruct;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.edu.tks.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SOAPApplication.class)
class RentalSoapServiceTest {

    private static List<RentalSOAP> testDataActive;
    private static List<RentalSOAP> testDataArchive;

    @LocalServerPort
    private int port;

    private RentalClient rentalClient;

    @PostConstruct
    private void init() throws DatatypeConfigurationException {
        String BASE_PATH = "http://localhost:" + port;

        Jaxb2Marshaller marshaller = getRentalMarshaller();
        rentalClient = new RentalClient(port);
        rentalClient.setDefaultUri(BASE_PATH + "/ws");
        rentalClient.setMarshaller(marshaller);
        rentalClient.setUnmarshaller(marshaller);

        testDataActive = new ArrayList<>();
        testDataArchive = new ArrayList<>();
        RentalSOAP rental;
        XMLGregorianCalendar rentDate;
        XMLGregorianCalendar expectedReturnDate;
        XMLGregorianCalendar actualReturnDate;

        rental = new RentalSOAP();
        rental.setRentalID("418d0406-e040-408a-abf3-7788db83b889");
        rental.setClientID("cebbee82-2398-4dc2-a94d-a4c863286ff0");
        rental.setRecordID("02cf35bf-d025-440b-a6ec-17cc6c77b021");
        rentDate = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
                2022, 5, 3, DatatypeConstants.FIELD_UNDEFINED);
        expectedReturnDate = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
                2022, 5, 10, DatatypeConstants.FIELD_UNDEFINED);
        rental.setRentDate(rentDate);
        rental.setExpectedReturnDate(expectedReturnDate);
        rental.setActive(true);
        testDataActive.add(rental);

        rental = new RentalSOAP();
        rental.setRentalID("53d3b654-c542-11ec-9d64-0242ac120002");
        rental.setClientID("c6f3fbf7-135b-498e-8154-3a5b9d291145");
        rental.setRecordID("3e3719e5-8689-4e65-883f-4cd06cae7195");
        rentDate = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
                2022, 1, 12, DatatypeConstants.FIELD_UNDEFINED);
        expectedReturnDate = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
                2022, 1, 19, DatatypeConstants.FIELD_UNDEFINED);
        rental.setRentDate(rentDate);
        rental.setExpectedReturnDate(expectedReturnDate);
        rental.setActive(true);
        testDataActive.add(rental);

        rental = new RentalSOAP();
        rental.setRentalID("1fa7cdf2-2b0c-428b-bbf7-a593d56e3b74");
        rental.setClientID("c6f3fbf7-135b-498e-8154-3a5b9d291145");
        rental.setRecordID("3e3719e5-8689-4e65-883f-4cd06cae7195");
        rentDate = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
                2021, 2, 13, DatatypeConstants.FIELD_UNDEFINED);
        expectedReturnDate = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
                2021, 2, 20, DatatypeConstants.FIELD_UNDEFINED);
        actualReturnDate = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
                2021, 2, 20, DatatypeConstants.FIELD_UNDEFINED);
        rental.setRentDate(rentDate);
        rental.setExpectedReturnDate(expectedReturnDate);
        rental.setActualReturnDate(actualReturnDate);
        rental.setActive(false);
        testDataArchive.add(rental);
    }

    private void compareRentals(RentalSOAP r1, RentalSOAP r2, boolean compareIDs) {
        if (compareIDs) assertEquals(r1.getRentalID(), r2.getRentalID());
        assertEquals(r1.getClientID(), r2.getClientID());
        assertEquals(r1.getRecordID(), r2.getRecordID());
        assertEquals(r1.getRentDate(), r2.getRentDate());
        assertEquals(r1.getExpectedReturnDate(), r2.getExpectedReturnDate());
        assertEquals(r1.getActualReturnDate(), r2.getActualReturnDate());
        assertEquals(r1.isActive(), r2.isActive());
    }


    @Test
    public void testGetAllRentals() {
        List<RentalSOAP> rentals = rentalClient.getAllRentals().getRental();

        for (int i = 0; i < testDataActive.size(); i++) {
            compareRentals(testDataActive.get(i), rentals.get(i), true);
        }
    }

    @Test
    public void testGetAllArchiveRentals() {
        List<RentalSOAP> rentals = rentalClient.getAllArchiveRentals().getRental();

        for (int i = 0; i < testDataArchive.size(); i++) {
            compareRentals(testDataArchive.get(i), rentals.get(i), true);
        }
    }

    @Test
    public void returnAndRentTheSameRecord() {
        RentalSOAP rental = testDataActive.get(0);

        RentalSOAP returnRental = rentalClient.returnRecord(
                rental.getClientID(), rental.getRentalID()).getRental();
        assertEquals(rental.getRentalID(), returnRental.getRentalID());
        assertEquals(rental.getClientID(), returnRental.getClientID());
        assertEquals(rental.getRecordID(), returnRental.getRecordID());
        assertEquals(rental.getRentDate(), returnRental.getRentDate());
        assertEquals(rental.getExpectedReturnDate(), returnRental.getExpectedReturnDate());
        assertNull(rental.getActualReturnDate());
        assertFalse(returnRental.isActive());

        System.out.println(rental.getClientID());

        RentalSOAP rentRental = rentalClient.rentRecord(
                rental.getClientID(), rental.getRecordID()).getRental();
        assertEquals(returnRental.getRecordID(), rentRental.getRecordID());
        assertEquals(returnRental.getClientID(), rentRental.getClientID());
        assertEquals(LocalDate.now().toString(), rentRental.getRentDate().toString());
        assertEquals(LocalDate.now().plusDays(7).toString(), rentRental.getExpectedReturnDate().toString());
        assertTrue(rentRental.isActive());
    }
}
