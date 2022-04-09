package model.rental;

import model.exceptions.SOAPRentalException;

import java.time.LocalDateTime;
import java.util.UUID;

public class RentalSOAPEntity {
    private final UUID rentalID;
    private final UUID clientID;
    private final UUID recordID;

    private final LocalDateTime rentDate;
    private LocalDateTime expectedReturnDate;
    private LocalDateTime actualReturnDate;
private boolean active;

    public RentalSOAPEntity(UUID rentalID, UUID clientID, UUID recordID, LocalDateTime rentDate,
                            LocalDateTime expectedReturnDate, LocalDateTime actualReturnDate, boolean active) {
        this.rentalID = rentalID;

        this.clientID = clientID;
        this.recordID = recordID;

        this.rentDate = rentDate;
        this.active = active;

        this.expectedReturnDate = expectedReturnDate;
        this.actualReturnDate = actualReturnDate;
    }

    public UUID getRentalID() {
        return rentalID;
    }

    public UUID getClientID() {
        return clientID;
    }

    public UUID getRecordID() {
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void extendReturnDays(int days) throws SOAPRentalException {
        if (days <= 0) {
            throw new SOAPRentalException("Wrong number of days");
        }

        this.expectedReturnDate = expectedReturnDate.plusDays(days);
    }


}
