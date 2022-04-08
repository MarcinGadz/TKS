package com.edu.tks.rental;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.RentalException;
import com.edu.tks.record.Record;
import com.edu.tks.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Rental {
    private UUID rentalID;
    private String clientID;
    private String recordID;

    private LocalDateTime rentDate;
    private LocalDateTime expectedReturnDate;
    private LocalDateTime actualReturnDate;
    private boolean active;

    public Rental(UUID rentalID, String clientID, String recordID, LocalDateTime rentDate, LocalDateTime returnDate,
                  LocalDateTime actualReturnDate, boolean active) throws InputException {
        this.rentalID = rentalID;

        this.clientID = clientID;
        this.recordID = recordID;

        this.rentDate = rentDate;
        this.active = active;

        this.expectedReturnDate = returnDate;
        this.actualReturnDate = actualReturnDate;
    }

    public Rental(UUID rentalID, User client, Record record) throws InputException {
        this(rentalID, client.getUserID().toString(), record.getRecordID().toString(), LocalDateTime.now(), LocalDateTime.now().plusDays(7), null, true);
        record.rent();
    }

    public Rental(User client, Record record) throws InputException {
        this(UUID.randomUUID(), client, record);
    }

    public Rental() {
    }

    public UUID getRentalID() {
        return rentalID;
    }

    public String getClientID() {
        return clientID;
    }

    public String getRecordID() {
        return recordID;
    }

    public LocalDateTime getRentDate() {
        return rentDate;
    }

    public LocalDateTime getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public LocalDateTime getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(LocalDateTime actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public int callCops() { return 997; }

    public boolean isActive() {
        return active;
    }

    public void archive() {
        this.active = false;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "clientID='" + clientID + '\'' +
                ", recordID='" + recordID + '\'' +
                ", rentDate=" + rentDate +
                ", expectedReturnDate=" + expectedReturnDate +
                ", actualReturnDate=" + actualReturnDate +
                ", active=" + active +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rental rental = (Rental) o;
        return Objects.equals(rentalID, rental.rentalID) && Objects.equals(clientID, rental.clientID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rentalID, clientID);
    }


    public void extendReturnDays(int days) throws RentalException {
        if (days <= 0) {
            throw new RentalException("Wrong number of days");
        }

        this.expectedReturnDate = expectedReturnDate.plusDays(days);
    }


}
