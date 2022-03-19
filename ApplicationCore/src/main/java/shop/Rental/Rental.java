package shop.Rental;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import shop.Exceptions.InputException;
import shop.Exceptions.PermissionException;
import shop.Exceptions.RentalException;
import shop.User.*;
import shop.Record.Record;

import java.time.LocalDateTime;
import java.util.UUID;

public class Rental {
    private final UUID rentalID;
    private final String clientID;
    private final String renterID;
    private final String recordID;

    private transient final User client;
    private transient final User renter;
    private transient final Record record;

    private final LocalDateTime rentDate;
    private LocalDateTime expectedReturnDate;
    private LocalDateTime actualReturnDate;

    public Rental(User client, User renter, Record record) throws PermissionException, InputException {
        this.rentalID = UUID.randomUUID();
        if (renter.getType() != UserType.RENTER) {
            throw new PermissionException("Indicated renter has no permissions to do this operation");
        }

        this.clientID = client.getUserID().toString();
        this.renterID = renter.getUserID().toString();
        this.recordID = record.getRecordID().toString();

        this.client = client;
        this.renter = renter;
        this.record = record;

        this.rentDate = LocalDateTime.now();
        this.record.rent(this);

        this.expectedReturnDate = this.rentDate.plusDays(7);
    }

    public UUID getRentalID() {
        return rentalID;
    }

    public User getClient() {
        return client;
    }

    public User getRenter() {
        return renter;
    }

    public String getClientID() {
        return clientID;
    }

    public String getRenterID() {
        return renterID;
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

    public Record getRecord() {
        return record;
    }

    public LocalDateTime getActualReturnDate() {
        return actualReturnDate;
    }

    public int callCops() { return 997; }

    public void returnRecord() throws InputException {
        this.actualReturnDate = LocalDateTime.now();
        this.record.release();
    }

    public void extendReturnDays(int days) throws RentalException {
        if (days <= 0) {
            throw new RentalException("Wrong number of days");
        }

        this.expectedReturnDate = expectedReturnDate.plusDays(days);
    }


}
