package com.edu.tks.ports.utils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateConversion {
    public static XMLGregorianCalendar convertToXMLGregorianCalendar(LocalDate date) {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
                    date.getYear(),
                    date.getMonthValue(),
                    date.getDayOfMonth(),
                    DatatypeConstants.FIELD_UNDEFINED
            );
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static XMLGregorianCalendar convertToXMLGregorianCalendar(LocalDateTime date) {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(
                    date.getYear(),
                    date.getMonthValue(),
                    date.getDayOfMonth(),
                    date.getHour(),
                    date.getMinute(),
                    date.getSecond(),
                    0,
                    DatatypeConstants.FIELD_UNDEFINED
            );
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static LocalDateTime convertToLocalDateTime(XMLGregorianCalendar date) {
        return LocalDateTime.of(
                date.getYear(),
                date.getMonth(),
                date.getDay(),
                date.getHour(),
                date.getMinute(),
                date.getSecond()
        );
    }
}